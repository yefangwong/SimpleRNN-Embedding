# SimpleRNN-Embedding Java Demo

This is a minimal character-based Simple RNN implemented in Java.  
It is currently able to learn short sequences like "牛肉麵" after limited training.  
The project is designed as a foundation for future natural language processing (NLP) experiments.

## ✨ Current Features
- Simple RNN implemented from scratch (using only Java standard libraries)
- Character-level training and inference
- Supports saving/loading trained model weights
- Training from short sequences
- Generation by sampling next character probabilities

## 🛣️ Roadmap

### ✅ Phase 1: Minimal Demo (Current)
- Train on small, fixed-length data (e.g. "牛肉麵")
- Generate text character by character
- Visualize loss during training

---

### 🚀 Phase 2: Scalability & Extensibility
- Refactor code to separate:
    - Model (weights, hidden state)
    - Training logic
    - Data preprocessing
- Read training data from external files instead of hard-coded strings
- Support configurable parameters (hidden size, sequence length, learning rate, etc.)
- Add smooth loss tracking & visualization

---

### 🧠 Phase 3: Model Enhancement
- Replace SimpleRNN with LSTM or GRU to capture longer dependencies
- Add word embeddings / character embeddings to improve generalization
- Support training on larger corpus (e.g., domain texts, OCR correction dataset)

---

### 🔍 Phase 4: Practical Application
- Connect to OCR output and build an automatic error correction tool
- Train text-to-SQL model on small dataset
- Integrate with external dictionaries or domain-specific knowledge

---

### ⚡ Phase 5: Performance & Research
- Add mini-batch training support
- Enable GPU acceleration (e.g., with DeepLearning4J or TensorFlow for Java)
- Explore quantum-inspired optimization or hardware acceleration

---

## 📂 Project Structure (planned)
```
SimpleRNN-Embedding/
├── src/
│ ├── model/ # Model classes (SimpleRNN, LSTM, etc.)
│ ├── data/ # Data loading & preprocessing
│ ├── train/ # Training loop & utils
│ ├── infer/ # Inference / generation
├── resources/ # Training data, saved models
├── README.md
└── pom.xml # (using Maven)
```
---

## 🤝 Contributing
PRs welcome! Feel free to fork and experiment, or discuss ideas in issues.

## 📜 License
MIT


## 🛣️ Roadmap

### ✅ Phase 1: Minimal Character-level SimpleRNN

* 實作最小可行版本的 SimpleRNN，支援 char-by-char 順序生成
* 支援 basic softmax + cross-entropy loss
* 訓練資料以短字串（如「牛肉麵」）為例
* 推理支援 sample / argmax 生成

---

### 🧠 Phase 2: Embedding Layer

* 改寫 RNN 輸入：從 one-hot 改為低維 embedding 向量
* 新增 `EmbeddingLayer` 類別，管理詞向量
* 調整 forward 與 backward，讓模型能處理 embedding 輸入
* 考慮初始化使用外部詞向量（如 fastText / Word2Vec）

---

### 📈 Phase 3: Scalability & Extensibility

* 加入 batch 訓練 (mini-batch SGD)
* 支援更長的訓練序列 (長文本)
* 儲存與載入訓練好的模型
* 評估 loss / accuracy

---

### 🔍 Phase 4: Application - OCR Correction

* 應用訓練好的 SimpleRNN/Embedding 做文字自動校正
* 收集 OCR 錯誤 → 正確的對照表
* 訓練模型預測正確文字
* 建立 demo: 輸入錯字，自動輸出建議修正

---

### 📦 Phase 5: Application - Text-to-SQL / 自然語言生成 SQL

* 探索 text-to-sql 生成任務的可行性
* 訓練小型模型，讓輸入自然語可生成簡單 SQL 查詢
* 分析生成正確率與失敗案例

---

### 🧩 Phase 6: Advanced

* 將 SimpleRNN 替換為 LSTM / GRU 增強長期記憶
* 引入 attention 機制
* 模型與 Ontology 知識庫結合，強化語意理解

---

### ⚛️ Phase 7: Quantum Acceleration (Research)

* 探索量子電路在簡化運算、收斂速度上的應用
* 嘗試用量子電腦模擬小規模 SimpleRNN
* 分析傳統與量子版本在小樣本 / 多樣本上的差異
* 預研量子機器學習框架（Qiskit、Pennylane 等）
