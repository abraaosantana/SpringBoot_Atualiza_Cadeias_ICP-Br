package br.gov.pa.tcm.atualizacao.certdigital.config;

import br.gov.pa.tcm.atualizacao.certdigital.infra.factory.SpringJobFactory;
import br.gov.pa.tcm.atualizacao.certdigital.infra.job.AtualizaCertJob;
import br.gov.pa.tcm.atualizacao.certdigital.infra.job.SimpleJob;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class SchedulerConfig {

	@Bean
	public JobFactory jobFactory(ApplicationContext applicationContext) {
		SpringJobFactory jobFactory = new SpringJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		return jobFactory;
	}

	@Bean
	public Properties quartzProperties() throws IOException {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory, Trigger simpleJobTrigger, CronTrigger atualizaCertCronJobTrigger) throws IOException {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setJobFactory(jobFactory);
		factory.setQuartzProperties(quartzProperties());
		factory.setTriggers(simpleJobTrigger, atualizaCertCronJobTrigger);
		System.out.println("Iniciando jobs....");
		return factory;
	}

	@Bean
	public SimpleTriggerFactoryBean simpleJobTrigger(
			@Qualifier("simpleJobDetail") JobDetail jobDetail,
			@Value("${simplejob.frequency}") long frequency) {
		System.out.println("Setando parametros: SimpleJobTrigger");

		SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
		factoryBean.setJobDetail(jobDetail);
		factoryBean.setStartDelay(0L);
		factoryBean.setRepeatInterval(frequency);
		factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
		return factoryBean;
	}

	@Bean
	public JobDetailFactoryBean simpleJobDetail() {
		JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
		factoryBean.setJobClass(SimpleJob.class);
		factoryBean.setDurability(true);
		return factoryBean;
	}

    //Esse Job é executado a cada 5 minutos, ou o que for definido no arquivo quartz.properties
    @Bean
    public SimpleTriggerFactoryBean atualizaCertJobTrigger(
            @Qualifier("atualizaCertJobDetail") JobDetail jobDetail,
            @Value("${atualizacert.frequency}") long frequency) {
        System.out.println("Setando parametros: atualizaCertJobTrigger");

        SimpleTriggerFactoryBean atualizaCertFactoryBean = new SimpleTriggerFactoryBean();
        atualizaCertFactoryBean.setJobDetail(jobDetail);
        atualizaCertFactoryBean.setStartDelay(0L);
        atualizaCertFactoryBean.setRepeatInterval(frequency);
        atualizaCertFactoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        return atualizaCertFactoryBean;
    }

    //Esse Job é executado de acordo com a expressao informada no arquivo aplication.propeties
    @Bean
    public CronTriggerFactoryBean atualizaCertCronJobTrigger(@Qualifier("atualizaCertJobDetail") JobDetail jobDetail
                                                            ,@Value("${scheduler.cron.cert.expressao}") String expressao){

        System.out.println("Setando parametros: AtualizaCertCronJobTrigger");
		System.out.println("AtualizaCertCronJobTrigger Expressao informada: "+expressao);

        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(jobDetail);
        stFactory.setStartDelay(3000);
        stFactory.setName("CertCronTrigger");
        stFactory.setGroup("cert");
        stFactory.setCronExpression(expressao);
        return stFactory;
    }

    @Bean
    public JobDetailFactoryBean atualizaCertJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(AtualizaCertJob.class);
        factoryBean.setDurability(true);
        return factoryBean;
    }
}