using System;
namespace CutreGest.model {
    public class Category {

        public ulong Id { get; set; }
        public string Name { get; set; }

        public Category(ulong id, string name) {
            Id = id;
            Name = name;
        }

        public Category() {
        }

        public override string ToString() {
            return String.Format("[Category Id = {0}, Name = {1}]", Id, Name);
        }
    }
}