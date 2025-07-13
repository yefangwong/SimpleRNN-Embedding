package idv.yf.ai.embedding;

public class EmbeddingLayer {
    private double[][] embeddings;
    private int vocabSize;
    private int embedDim;

    public EmbeddingLayer(int vocabSize, int embedDim) {
        this.vocabSize = vocabSize;
        this.embedDim = embedDim;
        embeddings = new double[vocabSize][embedDim];
        // 初始化為小隨機數
        for (int i = 0; i < vocabSize; i++) {
            for (int j = 0; j < embedDim; j++) {
                embeddings[i][j] = Math.random() * 0.01;
            }
        }
    }

    public double[] getEmbedding(int index) {
        return embeddings[index];
    }

    public int getEmbedDim() {
        return embedDim;
    }
}