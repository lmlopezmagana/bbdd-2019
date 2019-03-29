package com.salesianostriana.dam.primerejemplospringcore;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("beans.xml");
        
        //MiPrimerBean comoMeDeLaGana = (MiPrimerBean) appContext.getBean("saludator");
        ISaludador comoMeDeLaGana = appContext.getBean(ISaludador.class); 
        
        comoMeDeLaGana.setSaludo("Hola a todos los bicharracos!");
        
        comoMeDeLaGana.printSaludo();
        
        System.out.println(comoMeDeLaGana);
        
        MiPrimerBean otroBean = (MiPrimerBean) appContext.getBean("saludator");

        System.out.println(otroBean);
        
        otroBean.printSaludo();
        
        ((ClassPathXmlApplicationContext) appContext).close();
    }
}
