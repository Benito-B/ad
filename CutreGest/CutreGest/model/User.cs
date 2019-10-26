using System;
namespace CutreGest.model {
    public class User {

        public ulong Id { get; set; }
        public string Name { get; set; }
        public bool IsAdmin { get; set; }
        public string Password { get; set; }

        public User() { }

        public User(ulong id, string name, bool isAdmin) {
            Id = id;
            Name = name;
            IsAdmin = isAdmin;
        }

        public override string ToString() {
            return string.Format("[User: Id={0}, Name={1}, IsAdmin={2}]", Id, Name, IsAdmin);
        }
    }
}
