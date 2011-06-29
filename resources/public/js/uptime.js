var uptime = (function () {
    var setServices = function (services) {
        var ss = $("ul#services");
        
        ss.children().remove();
        
        jQuery.each(services, function(i, s) {
            $('<li><a href="' + s.url + '">' + s.name + '</a> <span class="votes">(' + s.votes.length + ')</span></li>').appendTo(ss);
        });
    };

    var refresh = function () {
        $.get("/services", setServices);
    };

    $(function () {
        $("button#refresh").click(refresh);
        refresh();
    });
}());
