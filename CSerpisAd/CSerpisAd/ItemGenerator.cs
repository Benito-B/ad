using System;
using System.Reflection;

namespace CSerpisAd {
    public class ItemGenerator {
        public ItemGenerator() {
        }

        public static void GenerateItem(Type type) {
            Object obj = Activator.CreateInstance(type);
            foreach(PropertyInfo p in obj.GetType().GetProperties()) {

            }
        }
    }
}
