using System;
namespace CutreGest.model {
    public class Movement {

        public const string M_OUT = "Salida";
        public const string M_IN = "Entrada";

        public Article Article { get; set; }
        public string Type { get; set; }
        public int Ammount { get; set; }
        public DateTime Date { get; set; }

        public Movement(Article article, string type, int ammount, DateTime date) {
            Article = article;
            Type = type;
            Ammount = ammount;
            Date = date;
        }

        public Movement() {
        }
    }
}
