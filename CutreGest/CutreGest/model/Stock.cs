using System;
namespace CutreGest.model {
    public class Stock {
    
        public ulong ArticleId { get; set; }
        public int CurrentStock { get; set; }
        public int MinStock { get; set; }
        public int MaxStock { get; set; }

        public Stock(ulong articleId, int currentStock, int minStock, int maxStock) {
            ArticleId = articleId;
            CurrentStock = currentStock;
            MinStock = minStock;
            MaxStock = maxStock;
        }

        public Stock() {
        }

        public override string ToString() {
            return CurrentStock.ToString();
        }
    }
}
