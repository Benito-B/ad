using System;
using System.Collections.Generic;

namespace CSerpisAd {
    public class Menu {

        private IList<string> labels = new List<string>();
        private IDictionary<string, Action> actions = new Dictionary<string, Action>();

        //Constructor privado para evitar instanciación sin Create
        private Menu(string menuLabel) {
            labels.Add(menuLabel);
        }


        //Función estática para crear nuevos menús asignando el constructor privado para crearlo
        public static Menu Create(string menuLabel) {
            return new Menu(menuLabel);
        }

        public Menu Add(string label, Action action) {
            labels.Add(label);
            actions.Add(label.Substring(0,1).ToLower(), action);
            return this;
        }

        public Menu ExitWhen(string label) {
            labels.Add(label);
            actions.Add(label.Substring(0,1), () => Environment.Exit(0));
            return this;
        }

        public void Loop() {
            while (true) {
                foreach (string label in labels) {
                    Console.WriteLine(label);
                }
                string option = Console.ReadLine();
                if (actions.ContainsKey(option)) {
                    actions[option]();
                } else {
                    Console.WriteLine("Esa opción no existe, porfavor elige otra.");
                }
            }

        }
    }
}
