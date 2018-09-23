package com.davromalc.demotwitterkafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;

@SpringBootApplication
public class DemoTwitterKafkaConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoTwitterKafkaConsumerApplication.class, args);
	}

	@Configuration
	@EnableKafkaStreams
	static class KafkaConsumerConfiguration {
		
		@Bean
		KStream<String, String> stream(StreamsBuilder streamBuilder){
			final KStream<String, String> stream = streamBuilder.stream("tweets");
			// Extract a key from the input
			// Group by key
			// Count tweets by user
			// Get tweet with more likes
			// Upsert into PostgreSQL
			return stream;
		}
		
		@Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
		public StreamsConfig kStreamsConfigs(KafkaProperties kafkaProperties) {
			Map<String, Object> props = new HashMap<>();
			props.put(StreamsConfig.APPLICATION_ID_CONFIG, "demo-twitter-kafka-application");
			props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
			props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
			props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
			return new StreamsConfig(props);
		}
		
	}
}
