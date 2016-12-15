package com.lab.hellocamel;

import java.io.File;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.FileComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.pollconsumer.quartz2.QuartzScheduledPollConsumerScheduler;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Testing Apache Camel");
        
        doFileSetup();
        
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {

            @Override
            public void configure() throws Exception {

                //To move all file from sourceLocation folde to destLocation folder from c drive
                from("file:/home/nat/Desktop/testcamel/source?delay=100&noop=true&useFixedDelay=true&scheduler=quartz2&scheduler.cron=0/10+*+*+*+*+?").process(new Processor() {

                    public void process(Exchange arg0) throws Exception {
                    	
                        System.out.println("Processing file: " + arg0.getIn().getHeader("CamelFileName"));
                    }
                }).
                        to("file:/home/nat/Desktop/testcamel/target").end();
            }
        });
        context.start();
        Thread.sleep(3000);
        // context.stop();
    }
    

    public static void doFileSetup() throws Exception {
        File f = new File("/home//nat/Desktop/testcamel/source");
        f.delete();
        f.mkdir();
        File fs1 = new File("/home/nat/Desktop/testcamel/source/text1.txt");
        fs1.delete();
        fs1.createNewFile();
        File f1 = new File("/home/nat/Desktop/testcamel/target");
        f1.delete();
        f1.mkdir();
    }    
}
