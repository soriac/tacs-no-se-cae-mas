package org.tacs.grupocuatro;

import io.javalin.Javalin;
import org.hibernate.Transaction;
import org.tacs.grupocuatro.entity.Student;

public class Server {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8080);
        app.get("/", ctx -> ctx.html("Hello, Javelin"));

        hibernateTest();
    }

    private static void hibernateTest() {
        Transaction transaction = null;
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            var student = new Student("Cristian", "Soria", "sbcristiansoria@gmail.com");
            session.save(student);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }

    }
}
