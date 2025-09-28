local key = KEYS[1]
local limit = tonumber(ARGV[1])
local window_size = tonumber(ARGV[2])
local current_time = tonumber(ARGV[3])

--increment counter
local current_count = redis.call('INCR', key)

--set expiry only if this is the first request in the window
if current_count == 1 then
    redis.call('EXPIRE', key, math.ceil(window_size / 1000))
end

return current_count