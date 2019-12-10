package com.fuhan.v17productservice;

import com.fuhan.entity.ProductType;
import com.fuhan.interfaces.IProductTypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class V17ProductServiceApplicationTests {

    @Autowired
    private RedisTemplate<String, List<ProductType>>  redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    void contextLoads() throws InterruptedException {
        List<ProductType> productTypes = redisTemplate.opsForValue().get("productType:list");
        if(productTypes == null || productTypes.size() == 0){
            String uuid = UUID.randomUUID().toString();
            Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent("lock", uuid, 1, TimeUnit.MINUTES);
            if(lock){
                System.out.println("数据库操作");
                productTypes.add(new ProductType());
                productTypes.add(new ProductType());
                Thread.sleep(10);
                redisTemplate.opsForValue().set("productType:list",productTypes);
            }
        }
    }

    @Test
    public void lockScriptTest(){
        //1.创建一个可以执行lua脚本的执行对象
        DefaultRedisScript<Boolean> lockScript = new DefaultRedisScript<>();
        //2.获取要执行的脚本
        lockScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lock.lua")));
        //3.设置返回类型
        lockScript.setResultType(Boolean.class);
        //4.封装参数
        List<String> keyList = new ArrayList<>();
        keyList.add("lock");
        String uuid = UUID.randomUUID().toString();
        keyList.add(uuid);
        keyList.add("60");
        //5.执行脚本
        Boolean result = redisTemplate.execute(lockScript, keyList);
        System.out.println(result);

        Long expire = redisTemplate.getExpire("lock");
        System.out.println(expire);
    }


    public Boolean bloomAdd(String key,String value){
        //1.创建一个可以执行lua脚本的执行对象
        DefaultRedisScript<Boolean> lockScript = new DefaultRedisScript<>();
        //2.获取要执行的脚本
        lockScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("bloomAdd.lua")));
        //3.设置返回类型
        lockScript.setResultType(Boolean.class);
        //4.封装参数
        List<String> keyList = new ArrayList<>();
        keyList.add(key);
        keyList.add(value);
        //5.执行脚本
        Boolean result =  redisTemplate.execute(lockScript, keyList);
        return result;
    }

    public Boolean bloomExists(String key,String value){
        //1.创建一个可以执行lua脚本的执行对象
        DefaultRedisScript<Boolean> lockScript = new DefaultRedisScript<>();
        //2.获取要执行的脚本
        lockScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("exists.lua")));
        //3.设置返回类型
        lockScript.setResultType(Boolean.class);
        //4.封装参数
        List<String> keyList = new ArrayList<>();
        keyList.add(key);
        keyList.add(value);
        //5.执行脚本
        Boolean result =  redisTemplate.execute(lockScript, keyList);
        return result;
    }

    public Boolean initBloom(String key,String err,String size){

//        //1.创建一个可以执行lua脚本的执行对象
//        DefaultRedisScript<Boolean> lockScript = new DefaultRedisScript<>();
//        //2.获取要执行的脚本
//        lockScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("setError.lua")));
//        //3.设置返回类型
//        lockScript.setResultType(Boolean.class);
//        //4.封装参数
//        List<String> keyList = new ArrayList<>();
//        keyList.add(key);
//        keyList.add(err);
//        keyList.add(size);
//        //5.执行脚本
//        Boolean result =  redisTemplate.execute(lockScript, keyList);
//        System.out.println(result);
//        return result;
        return executeLua("setError.lua",key,err,size);
    }

    public Boolean executeLua(String luaPath,String...strs){
        //1.创建一个可以执行lua脚本的执行对象
        DefaultRedisScript<Boolean> lockScript = new DefaultRedisScript<>();
        //2.获取要执行的脚本
        lockScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(luaPath)));
        //3.设置返回类型
        lockScript.setResultType(Boolean.class);
        //4.封装参数
        List<String> keyList = new ArrayList<>(strs.length);
        Collections.addAll(keyList, strs);
        //5.执行脚本
        Boolean result =  redisTemplate.execute(lockScript, keyList);
        return result;
    }


    @Test
    public void bloomTest(){
        initBloom("fuhan","0.5","100");
        for(int i=1; i<=100;i++){
            bloomAdd("fuhan",i+"");
        }
        List<Boolean> list = new ArrayList<>();
        for(int i=101;i<=2000;i++){
            Boolean flag = bloomExists("fuhan", i + "");
            if(flag){
                list.add(flag);
            }
        }
        System.out.println(list.size());
    }


    public static void main(String[] args) {
        short i = 1;
        long j = i;
        new Child("mai");

    }

   static class People {

        String name;

        public People() {

            System.out.print(1);

        }

        public People(String name) {

            System.out.print(2);

            this.name = name;

        }

    }

  static   class Child extends People {

        People father;

        public Child(String name) {

            System.out.print(3);

            this.name = name;

            father = new People(name + ":F");

        }

        public Child() {

            System.out.print(4);

        }

    }

}
