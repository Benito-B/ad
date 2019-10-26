using System;
using System.Collections.Generic;
using System.Data;
using CSerpisAd;
using CutreGest.logic;
using CutreGest.model;

namespace CutreGest.dao {
    public class CategoryDAO {

        private static string selectQuery = "select * from categories";

        public static ICollection<Category> GetAll() {
            ICollection<Category> categorias = new List<Category>();
            IDbConnection conn = DbConn.Instance.Connection;

            IDbCommand dbCommand = conn.CreateCommand();
            dbCommand.CommandText = selectQuery;
            IDataReader dataReader = dbCommand.ExecuteReader();

            while (dataReader.Read()) {
                categorias.Add(new Category((ulong)dataReader["id"], (string)dataReader["name"]));
            }
            dataReader.Close();
            return categorias;
        }

        private static string getQuery = "select from categories where id = @id";

        public static Category GetCategory(ulong id) {
            IDbConnection dbConn = DbConn.Instance.Connection;
            IDbCommand dbCommand = dbConn.CreateCommand();
            dbCommand.CommandText = getQuery;
            DbCommandHelper.AddParameter(dbCommand, "@id", id);
            IDataReader dataReader = dbCommand.ExecuteReader();
            dataReader.Read();
            dataReader.Close();
            return new Category((ulong)dataReader["id"], (string)dataReader["name"]);
        }

        public static void Save(Category c) {
            if (c.Id == 0)
                insert(c);
            else
                update(c);
        }

        private static string deleteQuery = "delete from categories where id = @id";

        public static void Delete(Category c) {
            IDbCommand dbCommand = DbConn.Instance.Connection.CreateCommand();
            dbCommand.CommandText = deleteQuery;
            DbCommandHelper.AddParameter(dbCommand, "@id", c.Id);
            dbCommand.ExecuteNonQuery();
        }

        private static string insertQuery = "insert into categories(name) values (@name)";

        private static void insert(Category c) {
            IDbConnection dbConn = DbConn.Instance.Connection;
            IDbCommand dbCommand = dbConn.CreateCommand();
            dbCommand.CommandText = insertQuery;
            DbCommandHelper.AddParameter(dbCommand, "@name", c.Name);
            dbCommand.ExecuteNonQuery();
        }

        private static string updateQuery = "update categories set name = @name where id = @id";

        private static void update(Category c) {
            IDbConnection dbConn = DbConn.Instance.Connection;
            IDbCommand dbCommand = dbConn.CreateCommand();
            dbCommand.CommandText = updateQuery;
            DbCommandHelper.AddParameter(dbCommand, "@name", c.Name);
            DbCommandHelper.AddParameter(dbCommand, "@id", c.Id);
            dbCommand.ExecuteNonQuery();
        }
    }
}
