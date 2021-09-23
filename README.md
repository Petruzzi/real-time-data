#"# real-time-data" 

## Couchbase script

```javascript
function OnUpdate(doc, meta) {
    log('docId', meta.id);
    var request = {
        path:  "event/" + meta.id,
        body: doc
            };
    try {
        var response = curl('POST', url_api, request);
        log('response', response);
       } catch (e) {
        log('error', e);
       }
}
