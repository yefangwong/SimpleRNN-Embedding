package idv.yf.ai.rnn;

import idv.yf.ai.embedding.EmbeddingLayer;
import idv.yf.ai.embedding.EmbeddingLoader;
import idv.yf.ai.util.Utils;

import java.io.IOException;
import java.util.Map;
import java.util.Arrays;

public class SimpleRNNEmbedding {
    private EmbeddingLayer embeddingLayer;
    private double[][] wxh; // input -> hidden
    private double[][] whh; // hidden -> hidden
    private double[][] why; // hidden -> output
    private double[] bh;
    private double[] by;
    private int hiddenSize;
    private int outputSize;
    static Map<String, double[]> embeddings;

    public SimpleRNNEmbedding(int vocabSize, int embedDim, int hiddenSize, int outputSize) {
        embeddingLayer = new EmbeddingLayer(vocabSize, embedDim);
        wxh = new double[hiddenSize][embedDim];
        whh = new double[hiddenSize][hiddenSize];
        why = new double[outputSize][embedDim];
        bh = new double[hiddenSize];
        by = new double[outputSize];

        // 初始化
        initMatrix(wxh);
        initMatrix(whh);
        initMatrix(why);
    }

    private void initMatrix(double[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                mat[i][j] = Math.random() * 0.01;
            }
        }
    }

    // forward 一步：　輸入是詞的 index
    public double[] forward(int inputIdx, double[] prevHidden) {
        // 第 1 步：取得輸入 token 的 embedding
        double[] embed = embeddingLayer.getEmbedding(inputIdx);

        // 第 2 步：計算新的隱藏狀態 h
        double[] h = new double[hiddenSize];
        for (int i = 0; i < hiddenSize; i++) {
            double sum = bh[i]; // 加上 bias
            // embedding 部分的權重相乘
            for (int j = 0; j < embed.length; j++) {
                sum += wxh[i][j] * embed[j];
            }
            // 前一個時間步的隱藏層狀態也乘上權重
            for (int j = 0; j < prevHidden.length; j++) {
                sum += whh[i][j] * prevHidden[j];
            }
            // 加上激活函數 tanh
            h[i] = Math.tanh(sum);
        }

        // 第 3 步：用隱藏狀態算出 output
        double[] y = new double[outputSize];
        for (int i = 0; i < outputSize; i++) {
            double sum = by[i]; // 加上 bias
            for (int j = 0; j < hiddenSize; j++) {
                sum += why[i][j] * h[j];
            }
            y[i] = sum;
        }
        return y; // 返回預測值（logits）
    }

    /**
     * softmax：把 logits 轉成機率
     */
    private double[] softmax(double[] logits) {
        double max = Arrays.stream(logits).max().orElse(0.0);
        double sum = 0.0;
        double[] exps = new double[logits.length];
        for (int i = 0; i < logits.length; i++) {
            exps[i] = Math.exp(logits[i] - max);
            sum += exps[i];
        }
        for (int i = 0; i < exps.length; i++) {
            exps[i] /= sum;
        }
        return exps;
    }

    /**
     * 推理 demo：輸入一個起始詞 index，產生指定長度
     */
    public void generate(int startIdx, int length) {
        double[] hPrev = new double[hiddenSize];
        int currentIdx = startIdx;
        System.out.print("Generated: ");
        for (int step = 0; step < length; step++) {
            double[] logits = forward(currentIdx, hPrev);
            double[] probs = softmax(logits);

            // 取機率最大的作為下個字（也可 sample）
            int nextIdx = argmax(probs);
            System.out.print(nextIdx + " "); // demo 印 index，可用 map 轉回字串

            // 更新隱藏狀態
            // 注意：此處可以也返回 h，再更新 hPrev
            // 簡化版這裡不更新
            currentIdx = nextIdx;
        }
        System.out.println();
    }

    private int argmax(double[] probs) {
        double max = probs[0];
        int idx = 0;
        for (int i = 1; i < probs.length; i++) {
            if (probs[i] > max) {
                max = probs[i];
                idx = i;
            }
        }
        return idx;
    }

    private void train(int[] data, int i, double v) {
    }

    public static void main(String[] args) throws IOException {
        // 訓練的部分 TODO
        int vocabSize = 10; // 假設詞表大小
        int embedDim = 5;
        int hiddenSize = 16;
        int outputSize = vocabSize; // 通常跟 vocabSize 一樣

        SimpleRNNEmbedding rnn = new SimpleRNNEmbedding(vocabSize, embedDim, hiddenSize, outputSize);
        // 假設「牛肉麵#」編碼成 index: 0,1,2,3
        int[] data = {0,1,2,3};

        //rnn.train(data, 100, 0.1);
        // 測試產生
        //rnn.generate(0, 5);

        // 以下為推理的部分
        embeddings = EmbeddingLoader.load("data/embeddings.txt");

        String inputWord = "鮭魚"; // 測試：沒見過的詞
        String nearest = Utils.findNearestWord(inputWord, embeddings);
        System.out.println("Input: " + inputWord + " → Nearest: " + nearest);

        // 接下來就用 nearest 的向量作推理
        double[] inputVec = embeddings.get(nearest);
        // Dummy 推理（模擬）
        System.out.print("Generated: " + nearest);
        String[] sequence = {"生", "魚", "片"};
        for (String w : sequence) {
            System.out.print(w);
        }
        System.out.println();
    }
}
