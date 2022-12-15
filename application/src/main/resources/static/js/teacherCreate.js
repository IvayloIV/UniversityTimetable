$(window).on('load', () => {
    var emptyFreeTime = $('.empty-free-time').clone();

    $('.remove-free-time').on('click', removeFreeTime);
    $('.btn-success').on('click', addFreeTime);

    $('.create-teacher').on('click', e => {
        e.preventDefault();
        var freeDays = $('.free-time-day');
        for (var i = 0; i < freeDays.length - 1; i++) {
            var item = freeDays[i];
            $(item).css('background-color', '#e9ecef')
            $(item).removeAttr('disabled');
        }
        $('#create-teacher-form').submit();
    });

    function removeFreeTime(e) {
        var row = $(e.target).closest('.row');
        var rows = row.parent();
        row.remove();
        $(rows.find('.row').splice(1)).each(updateInputNames);
    }

    function addFreeTime(e) {
        var addButton = $(e.target);
        if (addButton.hasClass("fa-plus")) {
            addButton = addButton.parent();
        }
        addButton.blur();
        var row = addButton.closest('.row');
        var startTimeInput = $(row).find('.free-start-time');
        var endTimeInput = $(row).find('.free-end-time');
        var startTimeValidation = $(row).find('.start-time-validation');
        var endTimeValidation = $(row).find('.end-time-validation');
        var endTimeBeforeValidation = $(row).find('.end-time-before-validation');

        var isValid = true;
        if (startTimeInput.val().length === 0) {
            startTimeValidation.removeClass('d-none');
            isValid = false;
        } else {
            startTimeValidation.addClass('d-none');
        }

        if (endTimeInput.val().length === 0) {
            endTimeValidation.removeClass('d-none');
            isValid = false;
        } else {
            endTimeValidation.addClass('d-none');
        }

        if (isValid && endTimeInput.val() <= startTimeInput.val()) {
            endTimeBeforeValidation.removeClass('d-none');
            isValid = false;
        } else {
            endTimeBeforeValidation.addClass('d-none');
        }

        if (!isValid) {
            return;
        }

        $(row).find('.free-time-day').attr('disabled', 'disabled');
        startTimeInput.attr('readonly', 'readonly');
        endTimeInput.attr('readonly', 'readonly');

        var button = $(row).find('.btn-success');
        button.removeClass('btn-success');
        button.addClass('btn-danger remove-free-time');
        button.empty();
        button.append($('<i>').addClass('fa-solid fa-trash'));
        button.unbind('click');
        button.on('click', removeFreeTime);

        var rows = row.parent();
        var tempEmptyFreeTime = emptyFreeTime.clone();
        updateInputNames(rows.children().length - 3, emptyFreeTime);
        emptyFreeTime.find('button').on('click', addFreeTime);
        rows.append(emptyFreeTime);
        emptyFreeTime = tempEmptyFreeTime;
    }

    function updateInputNames(index, item) {
        $(item).find('.free-time-day').attr('name', `teacherFreeTime[${index}].day`);
        $(item).find('.free-start-time').attr('name', `teacherFreeTime[${index}].startTime`);
        $(item).find('.free-end-time').attr('name', `teacherFreeTime[${index}].endTime`);
    }
});