using System;
using System.Data;
using MySql.Data.MySqlClient;
using CSerpisAd;


namespace CArticulo {
    class MainClass {


        private static IDbConnection dbConn = new MySqlConnection("server=localhost;database=dbpruebas;user=root;password=sistemas;ssl-mode=none");

        public static void Main(string[] args) {
            dbConn.Open();
            Menu.Create("Menú principal:").Add("1 - Añadir artículos", NewArticle)
            .Add("2 - Listar articulos", GetArticles)
            .Add("3 - Eliminar artículo", DeleteArticle)
            .ExitWhen("0 - Salir").Loop();

            dbConn.Close();
        }

        private static void DeleteArticle() {
            int id;
            IDbCommand dbCommand = dbConn.CreateCommand();
            dbCommand.CommandText = "delete from articulo where id = @id";
            Console.Write("Introduce ID del artículo a borrar: ");
            id = int.Parse(Console.ReadLine());

            DbCommandHelper.AddParameter(dbCommand, "@id", id);
            if (dbCommand.ExecuteNonQuery() >= 1)
                Console.WriteLine("Eliminado artículo con id: {0}\n", id);
            else
                Console.WriteLine("Error eliminando artículo.\n");
        }

        private static void NewArticle() {
            string name;
            float price;
            int cat;
            IDbCommand dbCommand = dbConn.CreateCommand();
            dbCommand.CommandText = "insert into articulo( nombre, categoria, precio) values ( @name, @cat, @price)";

            Console.Write("Introduce nombre del artículo: ");
            name = Console.ReadLine();
            GetCategories();
            Console.Write("Introduce categoría del artículo: ");
            cat = int.Parse(Console.ReadLine());
            Console.Write("Introduce precio del artículo: ");
            price = float.Parse(Console.ReadLine());

            DbCommandHelper.AddParameter(dbCommand, "@name", name);
            DbCommandHelper.AddParameter(dbCommand, "@cat", cat);
            DbCommandHelper.AddParameter(dbCommand, "@price", price);
            if (dbCommand.ExecuteNonQuery() >= 1)
                Console.WriteLine("Artículo {0} con categoría {1} y precio {2} insertado correctamente.", name, cat, price);
            else
                Console.WriteLine("Error insertando el artículo");
        }

        private static void GetArticles() {
            IDbCommand dbCommand = dbConn.CreateCommand();
            dbCommand.CommandText = "select a.id, a.nombre, c.nombre as categoria, a.precio from articulo a, categoria c where a.categoria = c.id";
            IDataReader dataReader = dbCommand.ExecuteReader();
            Console.WriteLine("{0,-5} - {1,-25} - {2,-15} - {3,-5}", dataReader.GetName(0), dataReader.GetName(1),
                dataReader.GetName(2), dataReader.GetName(3));
            while (dataReader.Read()) {
                Console.WriteLine("{0,-5} - {1,-25} - {2,-15} - {3,-5}", dataReader.GetInt32(0), dataReader.GetString(1),
                    dataReader.GetString(2), dataReader.GetFloat(3));
            }
            dataReader.Close();
        }

        private static void GetCategories() {
            IDbCommand dbCommand = dbConn.CreateCommand();
            dbCommand.CommandText = "select id,nombre from categoria";
            IDataReader dataReader = dbCommand.ExecuteReader();
            Console.WriteLine("{0,-5} - {1,-25}", dataReader.GetName(0), dataReader.GetName(1));
            while (dataReader.Read()) {
                Console.WriteLine("{0,-5} - {1,-25}", dataReader.GetInt32(0), dataReader.GetString(1));
            }
            dataReader.Close();
        }

        private static void NewCategory() {
            IDbCommand dbCommand = dbConn.CreateCommand();
            Console.Write("Introduce el nombre de la nueva categoría: ");
            string cat = Console.ReadLine();
            dbCommand.CommandText = "Insert into categoria(nombre) values (@cat)";
            DbCommandHelper.AddParameter(dbCommand, "@cat", cat);
            if (dbCommand.ExecuteNonQuery() >= 1)
                Console.WriteLine("Categoría {0} insertada correctamente.", cat);
            else
                Console.WriteLine("Error insertando la categoría");
        }

    }
}
