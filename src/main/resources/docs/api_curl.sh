#! bin/bash -x 

# 接口测试 简单方便 后期使用 postman/jmeter 等系统工具 

# user apis 
curl -X GET localhost:8080/api/v1/users 
curl -X GET localhost:8080/api/v1/users/1 
curl -X POST  localhost:8080/api/v1/users -H "Content-type:application/json" -d '{}' 
curl -X DELETE localhost:8080/api/v1/users/1 
curl -X PUT localhost:8080/api/v1/users/1 -H "Content-type:application/json" -d '{}'


# ship apis 
curl -X GET localhost:8080/api/v1/ships 
curl -X GET localhost:8080/api/v1/ships/1    # userid
curl -X GET localhost:8080/api/v1/ships/1/1  # userid  shipid 
curl -x POST localhost:8080/api/v1/ships/1 -H "Content-type:application/json" -d '{}'   # userid  ship
curl -x DELETE localhost:8080/api/v1/ships/1/1   # userid  shipid 
curl -x PUT localhost:8080/api/v1/ships/1 -H "Content-type:application/json" -d '{}'   # userid  ship 


# ship track point apis 
curl -x GET localhost:8080/api/v1/shiptracks/1   # shipId 
curl -X POST localhost:8080/api/v1/shiptracks/1 -H "Content-type:application/json" -d '{}' # shipId  ShipTrackPoint  
curl -X DELETE localhost:8080/api/v1/shiptracks/1   # shipId 













