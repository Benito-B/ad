using System;
using CutreGest.model;
using System.Data;
using CutreGest.logic;
using CSerpisAd;
using System.Collections.Generic;

namespace CutreGest.dao
{
    public class UserDAO{

        private static string selectQuery = "select * from users";

        public static ICollection<User> GetAll() {
            ICollection<User> users = new List<User>();

            IDbCommand dbCommand = DbConn.Instance.Connection.CreateCommand();
            dbCommand.CommandText = selectQuery;
            IDataReader dataReader = dbCommand.ExecuteReader();

            while (dataReader.Read()) {
                users.Add(new User((ulong)dataReader["id"], (string)dataReader["name"],
                    (bool)dataReader["isAdmin"]));
            }
            dataReader.Close();
            return users;
        }

        private static string loginQuery = "select * from users where name = @name and pass = @pass";

        public static User Login(LoginUser loginUser) {
            User user = null;
            IDbConnection conn = DbConn.Instance.Connection;
            IDbCommand dbCommand = conn.CreateCommand();
            dbCommand.CommandText = loginQuery;

            DbCommandHelper.AddParameter(dbCommand, "name", loginUser.Name);
            DbCommandHelper.AddParameter(dbCommand, "pass", Hasher.SHA256(loginUser.Pass));

            IDataReader dataReader = dbCommand.ExecuteReader();
            if (dataReader.Read())
                user = new User((ulong)dataReader["id"],
                    (string)dataReader["name"], 
                    (bool)dataReader["isAdmin"]);
            dataReader.Close();
            return user;
        }

        public static void Save(User user) {
            if (user.Id > 0)
                update(user);
            else
                insert(user);
        }


        private static string deleteQuery = "delete from users where id = @id";

        public static void Delete(User user) {
            IDbCommand comm = DbConn.Instance.Connection.CreateCommand();
            comm.CommandText = deleteQuery;
            DbCommandHelper.AddParameter(comm, "@id", user.Id);
            comm.ExecuteNonQuery();
        }

        private static string updateQuery = "update users set name = @name, isAdmin = @isAdmin where id = @id";
        private static string updateQueryWithPass = "update users set name = @name, isAdmin = @isAdmin, " +
        	"pass = @pass where id = @id";

        private static void update(User user) {
            IDbCommand comm = DbConn.Instance.Connection.CreateCommand();
            comm.CommandText = updateQuery;
            if(!user.Password.Equals("")) {
                comm.CommandText = updateQueryWithPass;
                DbCommandHelper.AddParameter(comm, "@pass", Hasher.SHA256(user.Password));
            }
            DbCommandHelper.AddParameter(comm, "@name", user.Name);
            DbCommandHelper.AddParameter(comm, "@isAdmin", user.IsAdmin);
            DbCommandHelper.AddParameter(comm, "@id", user.Id);
            comm.ExecuteNonQuery();
        }


        private static string insertQuery = "insert into users(name, isAdmin, pass) values " +
        	"(@name, @isAdmin, @pass)";

        private static void insert(User user) {
            IDbCommand comm = DbConn.Instance.Connection.CreateCommand();
            comm.CommandText = insertQuery;
            DbCommandHelper.AddParameter(comm, "@name", user.Name);
            DbCommandHelper.AddParameter(comm, "@isAdmin", user.IsAdmin);
            DbCommandHelper.AddParameter(comm, "@pass", Hasher.SHA256(user.Password));
            comm.ExecuteNonQuery();
        }

    }
}
