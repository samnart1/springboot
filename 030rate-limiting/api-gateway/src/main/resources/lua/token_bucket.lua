local key = KEYS[1]
local capacity = tonumber(ARGV[1])
local refill_rate = tonumber(ARGV[2])
local current_time = tonumber(ARGV[3])
local requested_tokens = tonumber(ARGV[4])

local bucket = redis.call('HMGET', key, 'tokens', 'last_refill_time')
local tokens = tonumber(bucket[1]) or capacity
local last_refill_time = tonumber(bucket[2]) or current_time

local elapsed = math.max(0, current_time - last_refill_time)
local tokens_to_add = math.floor(elapsed * refill_rate / 1000)

tokens = math.min(capacity, tokens + tokens_to_add)

if tokens >= requested_tokens then
    tokens = tokens - requested_tokens

    redis.call('HMSET', key,
                'tokens', tokens,
                'last_refill_time', current_time)
    redis.call('EXPIRE', key, 3600) -- 1 hour TTL

    return tokens
else
    redis.call('HMSET', key,
                'tokens', tokens,
                'last_refill_time', current_time)
    redis.call('EXPIRE', key, 3600)

    redis -1
end