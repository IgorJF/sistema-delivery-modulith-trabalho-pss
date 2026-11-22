package br.ufes;

import br.ufes.presenter.TelaPrincipalPresenter;
import javax.swing.SwingUtilities;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(Main.class)
                .headless(false)
                .run(args);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                context.getBean(TelaPrincipalPresenter.class);
            }
        });
    }
}