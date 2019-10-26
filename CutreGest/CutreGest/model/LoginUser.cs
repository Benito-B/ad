using System;
namespace CutreGest.model {
    public class LoginUser {

        public string Name { get; set; }
        public string Pass { get; set; }

        public LoginUser(string name, string pass) {
            Name = name;
            Pass = pass;
        }
    }
}
