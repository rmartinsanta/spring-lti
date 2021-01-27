package rmartin.lti.server.service.impls;

import rmartin.lti.api.model.LTIContext;
import rmartin.lti.api.service.Redis;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisLaunchContextImpl implements Redis {

    private static final Logger logger = Logger.getLogger(RedisLaunchContextImpl.class);
    @Value("${server.context.expiresIn}")
    private long expiresInMillis;

    private BoundHashOperations<String, String, LTIContext> contexts;

    private RedisTemplate<String, String> redisTokens;

    private ValueOperations<String, String> tokens;
    private static final String CONTEXT_BOUND_KEY = "LaunchContext";
    private static final String PENDING_CONTEXT_BOUND_KEY = "PendingContext";

    @Autowired
    public RedisLaunchContextImpl(RedisConnectionFactory redisConnectionFactory, Jackson2JsonRedisSerializer<?> jsonSerializer) {

        RedisTemplate<String, LTIContext> redisContexts = new RedisTemplate<>();
        redisContexts.setConnectionFactory(redisConnectionFactory);
        redisContexts.setKeySerializer(new StringRedisSerializer());
        redisContexts.setHashKeySerializer(new StringRedisSerializer());
        redisContexts.setValueSerializer(jsonSerializer);
        redisContexts.setHashValueSerializer(jsonSerializer);
        redisContexts.afterPropertiesSet();

        this.contexts = redisContexts.boundHashOps(CONTEXT_BOUND_KEY);

        this.redisTokens = new RedisTemplate<>();
        redisTokens.setConnectionFactory(redisConnectionFactory);
        redisTokens.setKeySerializer(new StringRedisSerializer());
        redisTokens.setValueSerializer(new StringRedisSerializer());
        redisTokens.afterPropertiesSet();

        tokens = redisTokens.opsForValue();
    }

    @Override
    public LTIContext getLTIContext(String id){
        LTIContext context = contexts.get(id);

        if(context == null){
            throw new IllegalArgumentException("Invalid context: "+id);
        }

        Boolean confirmDelete = redisTokens.delete(id);
        if(confirmDelete == null || !confirmDelete){
            logger.error("USING EXPIRED OR ALREADY USED CONTEXT, RESTRICTION NOT ENFORCED: "+context.getId());
        }

        return context;
    }

    @Override
    public void saveLTIContext(LTIContext context, String key){
        contexts.put(key, context);
        //this.setExpire(key);
    }


    // TODO review why this is neccesary? We can just leave the contexts there.
    private void setExpire(String key) {
        tokens.set(key, "");
        redisTokens.expire(key, expiresInMillis, TimeUnit.MILLISECONDS);
    }
}
