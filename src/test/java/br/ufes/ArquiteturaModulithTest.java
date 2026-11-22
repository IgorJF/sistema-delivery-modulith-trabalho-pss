package br.ufes;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class ArquiteturaModulithTest {
    @Test
    void verificarArquiteturaModular() {
        ApplicationModules modules = ApplicationModules.of("br.ufes");
        modules.forEach(System.out::println);
        modules.verify();
    }
}
