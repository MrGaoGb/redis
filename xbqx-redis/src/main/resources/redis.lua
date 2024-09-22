local key = KEYS[1] --限流KEY(一秒一个)
local limit = tonumber(ARGV[1]) --限流大小
local timeout = tonumber(ARGV[2]) -- 过期时间
local current = tonumber(redis.call('GET', key) or "0")
if redis.call('exists', key) == 1 then
    local val = redis.call('get', key)
    if current + 1 > limit then
        --如果超出限流大小
        return 0
    end
        redis.call("INCRBY", key, "1") -- 请求数 +1
        return 1
end
    redis.call('SET', KEYS[1], 1)
    redis.call('expire',key,timeout)
    return 1

-- redis.call('SET', KEYS[1], ARGV[1])
--return 1
--local key = KEYS[1]
--local argv1 = ARGV[1]
--local argv2 = ARGV[2]
--if (redis.call('exists', key) == 1) then
--    local val = redis.call('GET', key)
--    if val == "0" then
--        redis.call('set', key, argv1)
--        redis.call('expire', key, argv2)
--        return 1
--    end
--        return val
--end
-- return -1