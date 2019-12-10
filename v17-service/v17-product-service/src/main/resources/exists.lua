--
-- Created by IntelliJ IDEA.
-- User: Administrator
-- Date: 2019/11/28
-- Time: 17:09
-- To change this template use File | Settings | File Templates.
--
local bloomName = KEYS[1]
local value = KEYS[2]

-- bloomFilter
local result_1 = redis.call('BF.EXISTS', bloomName, value)
return result_1

