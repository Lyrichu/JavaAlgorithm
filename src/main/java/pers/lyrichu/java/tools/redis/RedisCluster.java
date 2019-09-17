package pers.lyrichu.java.tools.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RedisCluster {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisCluster.class);
    private String prefix = "";
    private String redisNodeString;
    private int ttl = -1; // time to live
    private static RedissonClient redisClient;
    private static RedisCluster instance;
    private static final String KEYNAME = "lock";
    private static RLock rLock;
    private static final int AUTO_UNLOCK_TIME = 1000; // 默认自动解锁时间

    public static synchronized RedisCluster getInstance(String redisNodeString) {
        if (instance == null) {
            synchronized (RedisCluster.class) {
                if (instance == null) {
                    instance = new RedisCluster(redisNodeString);
                }
            }
        }
        return instance;
    }

    public static synchronized RedisCluster getInstance(String redisNodeString,String prefix) {
        if (instance == null) {
            synchronized (RedisCluster.class) {
                if (instance == null) {
                    instance = new RedisCluster(redisNodeString,prefix);
                }
            }
        }
        return instance;
    }

    public static synchronized RedisCluster getInstance(String redisNodeString,String prefix,int ttl) {
        if (instance == null) {
            synchronized (RedisCluster.class) {
                if (instance == null) {
                    instance = new RedisCluster(redisNodeString,prefix,ttl);
                }
            }
        }
        return instance;
    }

    private RedisCluster(String redisNodeString) {
        this.redisNodeString = redisNodeString;
        init(getHostAndPortList(redisNodeString));
    }

    private RedisCluster(String redisNodeString,String prefix) {
        this.prefix = prefix;
        this.redisNodeString = redisNodeString;
        init(getHostAndPortList(redisNodeString));
    }

    private RedisCluster(String redisNodeString,String prefix,int ttl) {
        this.prefix = prefix;
        this.redisNodeString = redisNodeString;
        this.ttl = ttl;
        init(getHostAndPortList(redisNodeString));
    }

    private synchronized void init(List<String> hostAndPortList) {
        Config config = new Config();
        ClusterServersConfig csConfig = config.useClusterServers();
        for (String hostAndPort:hostAndPortList) {
            csConfig.addNodeAddress(hostAndPort);
        }
        redisClient = Redisson.create(config);
        rLock = redisClient.getLock(KEYNAME);
    }

    private List<String> getHostAndPortList(String s) {
        String[] splits = s.split(",");
        List<String> hostAndPortList = new ArrayList<>();
        for (String hostAndPort:splits) {
            if (hostAndPort.split(":").length == 2) {
                hostAndPortList.add(hostAndPort);
            }
        }
        return hostAndPortList;
    }

    public String get(String key) {
        rLock.lock(AUTO_UNLOCK_TIME, TimeUnit.MILLISECONDS);
        RBucket<String> rBucket = redisClient.getBucket(prefix + key);
        String value = rBucket.get();
        rLock.unlock();
        return value;
    }

    public void set(String key,String value) {
        set(prefix + key,value,ttl);
    }

    public void set(String key,String value,int ttl) {
        rLock.lock(AUTO_UNLOCK_TIME, TimeUnit.MILLISECONDS);
        RBucket<String> rBucket = redisClient.getBucket(prefix + key);
        rBucket.getAndSet(value,ttl,TimeUnit.SECONDS);
        rLock.unlock();
    }

    public Object getJsonField(String key,String jsonField) {
        String jsonValue = get(prefix + key);
        Object result = null;
        try {
            JSONObject json = JSON.parseObject(jsonValue);
            if (json.containsKey(jsonField)) {
                result = json.get(jsonField);
            }
        } catch (Exception e) {
            LOGGER.error("parse json error:{}",e);
        }
        return result;
    }



    public void setJsonField(String key,String jsonField,Object value,int ttl) {
        rLock.lock(AUTO_UNLOCK_TIME, TimeUnit.MILLISECONDS);
        RBucket<String> rBucket = redisClient.getBucket(prefix + key);
        String oldJsonValue = rBucket.get();
        JSONObject oldJsonObject;
        try {
            if (oldJsonValue == null) {
                oldJsonObject = new JSONObject();
            } else {
                oldJsonObject = JSON.parseObject(oldJsonValue);
            }
            oldJsonObject.put(jsonField,value);
            String newValue = oldJsonObject.toJSONString();
            rBucket.set(newValue,ttl,TimeUnit.SECONDS);
        } catch (Exception e) {
            LOGGER.error("parse json error:{}",e);
        }
        rLock.unlock();
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public String getRedisNodeString() {
        return redisNodeString;
    }

    public void setRedisNodeString(String redisNodeString) {
        this.redisNodeString = redisNodeString;
    }
}
