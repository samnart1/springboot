local key = KEYS[1]
local window_start = tonumber(ARGV[1])
local current_time = tonumber(ARGV[2])
local limit = tonumber(ARGV[3])

redis.call('ZREM')