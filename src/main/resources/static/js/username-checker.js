$("input[name=new-username]").on('change keyup paste', function () {
    $(this).removeClass('is-invalid');
    $(this).removeClass('is-valid');
    if ($(this).val().length >= 5) {
        $.ajax({
            url: '/profile/edit/username/check',
            type: 'post',
            data: 'username=' + $(this).val(),
            xhrFields: {
                withCredentials: true
            },
        }).done(function (data, statusText, xhr) {
            if (xhr.status == 202) {
                console.log('можно');
                $("input[name=new-username]").removeClass('is-invalid');
                $("input[name=new-username]").addClass('is-valid');
            } else if (xhr.status == 200) {
                console.log('нельзя');//todo Сделать изменения
                $("input[name=new-username]").removeClass('is-valid');
                $("input[name=new-username]").addClass('is-invalid');
            }
        });
    } else {
        $(this).removeClass('is-valid');
        $(this).addClass('is-invalid');
    }
});


