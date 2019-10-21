using System;
namespace CGtk{
    public class Categoria {
        public Categoria(string name, ulong id) {
            this.name = name;
            this.id = id;
        }

        private string name;
        private ulong id;

        public ulong Id { get => id; set => id = value; }
        public string Name { get => name; set => name = value; }

        public override string ToString() {
            return name;
        }
    }


}
