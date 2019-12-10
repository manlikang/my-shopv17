--
-- Created by IntelliJ IDEA.
-- User: Administrator
-- Date: 2019/11/28
-- Time: 17:14
-- To change this template use File | Settings | File Templates.
--
local key = KEYS[1]
local err = KEYS[2]
local size = KEYS[3]

local result = redis.call('bf.reserve', key,err,size);
return result;

