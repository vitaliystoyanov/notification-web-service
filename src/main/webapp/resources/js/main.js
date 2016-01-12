$(document).ready(function () {
    var map = new GMaps({
        div: '#map',
        lat: 50.4335063,
        lng: 30.5123802,
        zoom: 13,
        zoomControl: true,
        panControl: false,
        streetViewControl: false,
        mapTypeControl: false,
        overviewMapControl: false,
        clickable: false
    });

    window.setInterval(function () {
        loadEvents();
    }, 10000);

    function loadEvents() {
        $.ajax({
            type: 'GET',
            url: '/v1/events?systemId=55555555555555555555555555555558',
            dataType: 'json',
            success: function (data) {
                parse(JSON.parse(JSON.stringify(data)));
            }
        });
    }

    function parse(obj) {
        map.removeMarkers();
        for (var i = 0; i < obj["data"].length; i++) {
            var type = obj['data'][i]['type']['name'];
            var levelN = obj['data'][i]['danger']['name'];
            var levelL = obj['data'][i]['danger']['level'];
            var unix_timestamp = obj['data'][i]['createdAt'];
            var date = new Date(unix_timestamp * 1000);
            var hours = date.getHours();
            var minutes = "0" + date.getMinutes();
            var seconds = "0" + date.getSeconds();
            var when = hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2);
            var content = "<p>Type: " + type + "</br>Level danger: " + levelN + "-" + levelL + "</br><b>" +
                "When: " + when + " </b></p>";
            map.addMarker({
                lat: obj["data"][i]["location"]["latitude"],
                lng: obj["data"][i]["location"]["longitude"],
                //animation: google.maps.Animation.DROP,
                verticalAlign: 'bottom',
                horizontalAlign: 'center',
                backgroundColor: '#ffffff',
                infoWindow: {
                    content: content
                }
            });
        }
    }
});