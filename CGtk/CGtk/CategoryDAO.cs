using System;
using System.Collections.Generic;
using System.Data;
using CGtk.Articulo;
using CSerpisAd;

namespace CGtk {
    public class CategoryDAO {
        /*
         * Categoría (id, nombre)
         */

        private static string selectQuery = "select * from categoria";
        public static ICollection<Categoria> GetAll() {
            ICollection<Categoria> categorias = new List<Categoria>();
            IDbConnection conn = DbConn.GetInstance().GetConnection();

            IDbCommand dbCommand = conn.CreateCommand();
            dbCommand.CommandText = selectQuery;
            IDataReader dataReader = dbCommand.ExecuteReader();

            while (dataReader.Read()) {
                categorias.Add(new Categoria((string)dataReader["nombre"], (ulong)dataReader["id"]));
            }
            dataReader.Close();
            return categorias;
        }

        private static string getQuery = "select from categoria where id = @id";
        public static Categoria GetCategory(ulong id) {
            IDbConnection dbConn = DbConn.GetInstance().GetConnection();
            IDbCommand dbCommand = dbConn.CreateCommand();
            dbCommand.CommandText = getQuery;
            DbCommandHelper.AddParameter(dbCommand, "@id", id);
            IDataReader dataReader = dbCommand.ExecuteReader();
            dataReader.Read();
            dataReader.Close();
            return new Categoria((string)dataReader["nombre"], (ulong)dataReader["id"]);
        }

        public static void Save(Categoria c) {
            if (c.Id == 0)
                insert(c);
            else
                update(c);
        }

        private static string insertQuery = "insert into categoria(nombre) values (@nombre)";
        private static void insert(Categoria c) {
            IDbConnection dbConn = DbConn.GetInstance().GetConnection();
            IDbCommand dbCommand = dbConn.CreateCommand();
            dbCommand.CommandText = insertQuery;
            DbCommandHelper.AddParameter(dbCommand, "@nombre", c.Name);
            dbCommand.ExecuteNonQuery();
        }

        private static string updateQuery = "update categoria set nombre = @nombre where id = @id";
        private static void update(Categoria c) {
            IDbConnection dbConn = DbConn.GetInstance().GetConnection();
            IDbCommand dbCommand = dbConn.CreateCommand();
            dbCommand.CommandText = updateQuery;
            DbCommandHelper.AddParameter(dbCommand, "@nombre", c.Name);
            DbCommandHelper.AddParameter(dbCommand, "@id", c.Id);
        }
    }
}
