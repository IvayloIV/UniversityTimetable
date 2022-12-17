$(window).on('load', () => {
    var courseTime = $('.course-time').clone();

    $('.add-course-time').on('click', addCourseTime);

    function removeCourseTime(e) {
        var row = $(e.target).closest('.course-time');
        var rows = row.parent();
        row.remove();
        $(rows.find('.course-time')).each(updateInputNames);
    }

    function addCourseTime(e) {
        var addButton = $(e.target);
        if (addButton.hasClass("fa-plus")) {
            addButton = addButton.parent();
        }
        addButton.blur();

        var row = addButton.closest('.course-time');
        var dayInput = $(row).find('.course-time-day');
        var startTimeInput = $(row).find('.course-start-time');
        var endTimeInput = $(row).find('.course-end-time');
        var startTimeValidation = $(row).find('.start-time-validation');
        var endTimeEmptyValidation = $(row).find('.end-time-empty-validation');
        var endTimeBeforeValidation = $(row).find('.end-time-before-validation');
        var endTimeRangeValidation = $(row).find('.end-time-range-validation');

        var isValid = true;
        if (startTimeInput.val().length === 0) {
            startTimeValidation.removeClass('d-none');
            isValid = false;
        } else {
            startTimeValidation.addClass('d-none');
        }

        if (endTimeInput.val().length === 0) {
            endTimeEmptyValidation.removeClass('d-none');
            isValid = false;
        } else {
            endTimeEmptyValidation.addClass('d-none');
        }

        if (isValid && endTimeInput.val() <= startTimeInput.val()) {
            endTimeBeforeValidation.removeClass('d-none');
            isValid = false;
        } else {
            endTimeBeforeValidation.addClass('d-none');
        }

        if (isValid) {
            var rangeValidation = true;
            var courseTimes = row.parent().find('.course-time');
            courseTimes
                .splice(0, courseTimes.length - 1)
                .forEach(e => {
                    var day = $(e).find('.course-time-day').val();
                    var startTime = $(e).find('.course-start-time').val();
                    var endTime = $(e).find('.course-end-time').val();

                    if (day == dayInput.val() && !(startTimeInput.val() > endTime || endTimeInput.val() < startTime)) {
                        rangeValidation = false;
                    }
                });

            if (!rangeValidation) {
                endTimeRangeValidation.removeClass('d-none');
                isValid = false;
            } else {
                endTimeRangeValidation.addClass('d-none');
            }
        }

        if (!isValid) {
            return;
        }

        $(row).find('.course-time-day').attr('disabled', 'disabled');
        startTimeInput.attr('readonly', 'readonly');
        endTimeInput.attr('readonly', 'readonly');

        var button = $(row).find('.add-course-time');
        button.removeClass('btn-success add-course-time');
        button.addClass('btn-danger remove-course-time');
        button.empty();
        button.append($('<i>').addClass('fa-solid fa-trash'));
        button.unbind('click');
        button.on('click', removeCourseTime);

        var rows = row.parent();
        var tempCourseTime = courseTime.clone();
        updateInputNames(rows.find('.course-time').length, courseTime);
        courseTime.find('.add-course-time').on('click', addCourseTime);
        rows.append(courseTime);
        courseTime = tempCourseTime;
    }

    function updateInputNames(index, item) {
        var courses = $('.course-wrapper');
        $(item).find('.course-time-day').attr('name', `courses[${courses.length}].courseTimes[${index}].day`);
        $(item).find('.course-start-time').attr('name', `courses[${courses.length}].courseTimes[${index}].startTime`);
        $(item).find('.course-end-time').attr('name', `courses[${courses.length}].courseTimes[${index}].endTime`);
    }
});