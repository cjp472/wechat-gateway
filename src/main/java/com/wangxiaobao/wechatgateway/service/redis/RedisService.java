package com.wangxiaobao.wechatgateway.service.redis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
/**
  * @PackageName: com.source3g.member.service.redis
  * @ClassName: RedisService
  * @Description: redis操作类
  * @Copyright: Copyright (c) 2017  ALL RIGHTS RESERVED.
  * @Company:成都国盛天丰技术有限责任公司
  * @author ZhouTong
  * @date  2018/1/8 16:55
 */
@Service
public class RedisService{
  
    @Autowired  
    private RedisTemplate<String, ?> redisTemplate;  
      
    public boolean set(final String key, final String value) {  
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
              
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                connection.set(serializer.serialize(key), serializer.serialize(value));  
                return true;  
            }  
        });  
        return result;  
    }

    public boolean set(final String key, final String value , final long expire) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {

            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.setEx(serializer.serialize(key),expire, serializer.serialize(value));
                return true;
            }
        });
        return result;
    }
    
    public boolean del(final String key){
    	boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {

            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.del(serializer.serialize(key));
                return true;
            }
        });
        return result;
    }

    public String get(final String key){  
        String result = redisTemplate.execute(new RedisCallback<String>() {  
              
            public String doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] value =  connection.get(serializer.serialize(key));  
                return serializer.deserialize(value);  
            }  
        });  
        return result;  
    }  
  
      
    public boolean expire(final String key, long expire) {  
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);  
    }  
  
      
    public <T> boolean setList(String key, List<T> list) {  
        String value = JSONObject.toJSONString(list);
        return set(key,value);  
    }  
  
      
    public <T> List<T> getList(String key,Class<T> clz) {  
        String json = get(key);  
        if(json!=null){  
            List<T> list = JSONArray.parseArray(json, clz);
            return list;  
        }  
        return null;  
    }  
  
      
    public long lpush(final String key, Object obj) {  
        final String value = JSONObject.toJSONString(obj);
        long result = redisTemplate.execute(new RedisCallback<Long>() {  
              
            public Long doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                long count = connection.lPush(serializer.serialize(key), serializer.serialize(value));  
                return count;  
            }  
        });  
        return result;  
    }  
  
      
    public long rpush(final String key, Object obj) {  
        final String value = JSONObject.toJSONString(obj);
        long result = redisTemplate.execute(new RedisCallback<Long>() {  
              
            public Long doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                long count = connection.rPush(serializer.serialize(key), serializer.serialize(value));  
                return count;  
            }  
        });  
        return result;  
    }  
  
      
    public String lpop(final String key) {  
        String result = redisTemplate.execute(new RedisCallback<String>() {  
              
            public String doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] res =  connection.lPop(serializer.serialize(key));  
                return serializer.deserialize(res);  
            }  
        });  
        return result;  
    }

    public Boolean lock(String key) {
        return this.lock(key, 0);
    }

    public Boolean lock(String key, int retries) {
        Boolean lock = this.setnx(key, String.valueOf(System.currentTimeMillis()));
        if (lock) {
            return lock;
        } else {
            for(int i = 0; i < retries; ++i) {
                lock = this.setnx(key, String.valueOf(System.currentTimeMillis()));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {}
            }
            return lock;
        }
    }

    public Boolean setnx(String key, String val) {
        Boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                return connection.setNX(serializer.serialize(key),serializer.serialize(val));
            }
        });
        return result;
    }
  
}  