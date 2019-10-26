using System;
namespace CutreGest.model {
    public class Article {

        public ulong Id { get; set; }
        public string Name { get; set; }
        public ulong Category { get; set; }
        public double Price { get; set; }
        public Stock Stock { get; set; }

        public Article(ulong id, string name, ulong category, double price, Stock stock) {
            Id = id;
            Name = name;
            Category = category;
            Price = price;
            Stock = stock;
        }

        public Article(string name) {
            Name = name;
        }

        public Article() {
        }

        public override string ToString() {
            return Name;
        }
    }
}
