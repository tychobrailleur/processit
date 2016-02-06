require 'bunny'

conn = Bunny.new(hostname: 'localhost', admin: 'guest', password: 'guest')
conn.start

ch = conn.create_channel
x = Bunny::Exchange.new(ch, :direct, 'test.queue', durable: true, auto_delete: false)

data =<<DATA
{
  "code": "F111",
  "number": "5000030",
  "agent_id": 583
}
DATA

x.publish(data, routing_key: '')

conn.close
