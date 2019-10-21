using System;
namespace CGtk {
    public class Article {
        public Article() {
        }

        public ulong Id { get; set; }
        public string Name { get; set; }
        public Categoria Category { get; set; }
        public float Price { get; set; }

        public Article(ulong id, string name, Categoria categoria, float price) {
            Id = id;
            Name = name;
            Category = categoria;
            Price = price;
        }

    }
}
