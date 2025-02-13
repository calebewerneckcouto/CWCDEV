{"openapi":"3.0.1","info":{"title":"CWCDEV API","description":"CWCDEV API CODE","license":{"name":"Apache 2.0","url":"https://github.com/calebewerneckcouto/CWCDEV.git"},"version":"v0.0.1"},"servers":[{"url":"http://localhost:8080","description":"Generated server url"}],"tags":[{"name":"Code","description":"Controller for Code"}],"paths":{"/codigos/{id}":{"get":{"tags":["Code"],"summary":"Get code by id","description":"Get code by id","operationId":"findById","parameters":[{"name":"id","in":"path","required":true,"schema":{"type":"integer","format":"int64"}}],"responses":{"200":{"description":"Ok","content":{"*/*":{"schema":{"$ref":"#/components/schemas/CodigoDTO"}}}},"404":{"description":"Not Found","content":{"*/*":{"schema":{"$ref":"#/components/schemas/CodigoDTO"}}}}}},"put":{"tags":["Code"],"summary":"Update a new code","description":"Update a  code","operationId":"update","parameters":[{"name":"id","in":"path","required":true,"schema":{"type":"integer","format":"int64"}}],"requestBody":{"content":{"application/json":{"schema":{"$ref":"#/components/schemas/CodigoDTO"}}},"required":true},"responses":{"200":{"description":"Ok","content":{"*/*":{"schema":{"$ref":"#/components/schemas/CodigoDTO"}}}},"400":{"description":"Bad Request","content":{"*/*":{"schema":{"$ref":"#/components/schemas/CodigoDTO"}}}},"401":{"description":"Unauthorized","content":{"*/*":{"schema":{"$ref":"#/components/schemas/CodigoDTO"}}}},"403":{"description":"Forbidden","content":{"*/*":{"schema":{"$ref":"#/components/schemas/CodigoDTO"}}}},"404":{"description":"Not Found","content":{"*/*":{"schema":{"$ref":"#/components/schemas/CodigoDTO"}}}},"422":{"description":"Unprocessable Entity","content":{"*/*":{"schema":{"$ref":"#/components/schemas/CodigoDTO"}}}}},"security":[{"bearerAuth":[]}]},"delete":{"tags":["Code"],"operationId":"delete","parameters":[{"name":"id","in":"path","required":true,"schema":{"type":"integer","format":"int64"}}],"responses":{"200":{"description":"OK"}},"security":[{"bearerAuth":[]}]}},"/codigos":{"get":{"tags":["Code"],"summary":"List all codes","description":"Get All Codes","operationId":"findAll","responses":{"200":{"description":"Ok","content":{"*/*":{"schema":{"type":"array","items":{"$ref":"#/components/schemas/CodigoDTO"}}}}}}},"post":{"tags":["Code"],"summary":"Create a new Code","description":"Create a new Code","operationId":"insert","requestBody":{"content":{"application/json":{"schema":{"$ref":"#/components/schemas/CodigoDTO"}}},"required":true},"responses":{"201":{"description":"Created","content":{"*/*":{"schema":{"$ref":"#/components/schemas/CodigoDTO"}}}},"400":{"description":"Bad Request","content":{"*/*":{"schema":{"$ref":"#/components/schemas/CodigoDTO"}}}},"401":{"description":"Unauthorized","content":{"*/*":{"schema":{"$ref":"#/components/schemas/CodigoDTO"}}}},"403":{"description":"Forbidden","content":{"*/*":{"schema":{"$ref":"#/components/schemas/CodigoDTO"}}}},"422":{"description":"Unprocessable Entity","content":{"*/*":{"schema":{"$ref":"#/components/schemas/CodigoDTO"}}}}},"security":[{"bearerAuth":[]}]}},"/users/me":{"get":{"tags":["user-controller"],"operationId":"getMe","responses":{"200":{"description":"OK","content":{"*/*":{"schema":{"$ref":"#/components/schemas/UserDTO"}}}}},"security":[{"bearerAuth":[]}]}}},"components":{"schemas":{"CodigoDTO":{"required":["codigo","descricao","linguagem"],"type":"object","properties":{"id":{"type":"integer","format":"int64"},"linguagem":{"maxLength":50,"minLength":2,"type":"string"},"descricao":{"maxLength":200,"minLength":2,"type":"string"},"codigo":{"type":"string"},"imgUrl":{"type":"string"}}},"UserDTO":{"type":"object","properties":{"id":{"type":"integer","format":"int64"},"name":{"type":"string"},"email":{"type":"string"},"phone":{"type":"string"},"birthDate":{"type":"string","format":"date"},"roles":{"type":"array","items":{"type":"string"}}}}},"securitySchemes":{"bearerAuth":{"type":"http","scheme":"bearer"}}}}
