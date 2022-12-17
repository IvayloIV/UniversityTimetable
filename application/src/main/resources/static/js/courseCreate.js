$(window).on('load', () => {
    var newCourseWrapperCloning = $('.new-course-wrapper').clone();
    $('.create-course').on('click', addNewCourse);
    $('.remove-course').on('click', removeCourse);

    var departmentIdInputs = $(".department-id");
    departmentIdInputs.each((i, e) => {
        var departmentIdInput = $(e);
        $.get(`/department/${departmentIdInput.val()}`)
            .then((res) => {
                    var departmentSelect = departmentIdInput.parent().find('.department-select-disabled');
                    departmentSelect.append(
                        $('<option>').val(res.id).text(res.nameBg).attr("selected", true)
                    );
                    departmentIdInput.val('');
                    var specialtyIdInput = departmentSelect.closest('.course-wrapper').find('.specialty-id');
                    loadSpecialty(specialtyIdInput);
                }
            );
    });

    function loadSpecialty(specialtyIdInput) {
        $.get(`/specialty/${specialtyIdInput.val()}`)
            .then((res) => {
                    var specialtySelect = specialtyIdInput.parent().find('.specialty-select-disabled');
                    specialtySelect.append(
                        $('<option>').val(res.id).text(res.specialtyName).attr("selected", true)
                    );
                    specialtyIdInput.val('');
                }
            );
    }

    function addNewCourse(e) {
        var addButton = $(e.target);
        addButton.blur();

        var newCourseWrapper = addButton.closest('.new-course-wrapper');
        var createGroupHandler = $._data(newCourseWrapper.find('.create-group').get(0), "events").click[0].handler;
        var addCourseHandler = $._data(newCourseWrapper.find('.add-course-time').get(0), "events").click[0].handler;
        var degreeSelect = $(newCourseWrapper).find('.degree-select');
        var modeSelect = $(newCourseWrapper).find('.mode-select');
        var facultySelect = $(newCourseWrapper).find('.faculty-select');
        var weekSelect = $(newCourseWrapper).find('.week-select');
        var departmentSelect = $(newCourseWrapper).find('.department-select');
        var startWeekInput = $(newCourseWrapper).find('.start-week');
        var specialtySelect = $(newCourseWrapper).find('.specialty-select');
        var endWeekInput = $(newCourseWrapper).find('.end-week');
        var yearSelect = $(newCourseWrapper).find('.year-select');
        var hoursPerWeekInput = $(newCourseWrapper).find('.hours-per-week');
        var roomSelect = $(newCourseWrapper).find('.room-select');
        var meetingsPerWeekInput = $(newCourseWrapper).find('.meetings-per-week');
        var teacherSelect = $(newCourseWrapper).find('.teacher-select');

        var startWeekValidation = $(newCourseWrapper).find('.start-week-validation');
        var endWeekValidation = $(newCourseWrapper).find('.end-week-validation');
        var hoursPerWeekValidation = $(newCourseWrapper).find('.hours-per-week-validation');
        var meetingsPerWeekValidation = $(newCourseWrapper).find('.meetings-per-week-validation');

        var isValid = true;
        if (startWeekInput.val().length !== 0 && startWeekInput.val() <= 0) {
            startWeekValidation.removeClass('d-none');
            isValid = false;
        } else {
            startWeekValidation.addClass('d-none');
        }

        if (startWeekInput.val().length !== 0 &&
            (endWeekInput.val().length === 0 || endWeekInput.val() < startWeekInput.val())) {
            endWeekValidation.removeClass('d-none');
            isValid = false;
        } else {
            endWeekValidation.addClass('d-none');
        }

        if (hoursPerWeekInput.val().length === 0 || hoursPerWeekInput.val() <= 0) {
            hoursPerWeekValidation.removeClass('d-none');
            isValid = false;
        } else {
            hoursPerWeekValidation.addClass('d-none');
        }

        if (meetingsPerWeekInput.val().length === 0 || meetingsPerWeekInput.val() <= 0) {
            meetingsPerWeekValidation.removeClass('d-none');
            isValid = false;
        } else {
            meetingsPerWeekValidation.addClass('d-none');
        }

        if (!isValid) {
            return;
        }

        degreeSelect.attr('disabled', 'disabled');
        modeSelect.attr('disabled', 'disabled');
        facultySelect.attr('disabled', 'disabled');
        weekSelect.attr('disabled', 'disabled');
        departmentSelect.attr('disabled', 'disabled');
        startWeekInput.attr('readonly', 'readonly');
        specialtySelect.attr('disabled', 'disabled');
        endWeekInput.attr('readonly', 'readonly');
        yearSelect.attr('disabled', 'disabled');
        hoursPerWeekInput.attr('readonly', 'readonly');
        roomSelect.attr('disabled', 'disabled');
        meetingsPerWeekInput.attr('readonly', 'readonly');
        teacherSelect.attr('disabled', 'disabled');

        startWeekValidation.remove();
        endWeekValidation.remove();
        hoursPerWeekValidation.remove();
        meetingsPerWeekValidation.remove();

        newCourseWrapper.removeClass('new-course-wrapper');
        newCourseWrapper.addClass('course-wrapper');

        var removeInputText = addButton.parent().find('input');
        addButton.removeClass('btn-outline-success create-course');
        addButton.addClass('btn-danger remove-course');
        addButton.text(removeInputText.val());
        removeInputText.remove();
        addButton.unbind('click');
        addButton.on('click', removeCourse);

        newCourseWrapper.find('.remove-group').each((i, e) => e.remove());
        newCourseWrapper.find('.create-group').parent().remove();
        newCourseWrapper.find('.empty-group-validation').remove();
        newCourseWrapper.find('.group-exists-validation').remove();
        var groups = newCourseWrapper.find('.group');
        if (groups.length == 1) {
            groups[0].closest('.groups-flex').remove();
        } else {
            groups[groups.length - 1].remove();
        }

        var courseTimeWrapper = newCourseWrapper.find('.course-time-wrapper');
        var courseTimes = courseTimeWrapper.find('.course-time');
        if (courseTimes.length == 1) {
            courseTimeWrapper.remove();
        } else {
            courseTimes[courseTimes.length - 1].remove();
        }

        courseTimes.each((i, e) => {
            $(e).find('.remove-course-time').parent().remove();
        });

        var rows = newCourseWrapper.parent();
        var tempNewCourseWrapper = newCourseWrapperCloning.clone();
        updateNames(rows.children().length - 1, tempNewCourseWrapper);
        rows.append(tempNewCourseWrapper);
        tempNewCourseWrapper.find('.create-course').on('click', addNewCourse);
        tempNewCourseWrapper.find('.create-group').on('click', createGroupHandler);
        tempNewCourseWrapper.find('.add-course-time').on('click', addCourseHandler);
        loadDepartments(tempNewCourseWrapper.find('.faculty-select'),
            tempNewCourseWrapper.find('.department-select'),
            tempNewCourseWrapper.find('.specialty-select'));
    }

    function removeCourse(e) {
        var course = $(e.target).closest('.course-wrapper');
        var coursesWrapper = course.parent();
        course.remove();
        $(coursesWrapper.children().splice(1)).each(updateNames);
    }

    function updateNames(index, item) {
        $(item).find('.degree-select').attr('name', `courses[${index}].degree`);
        $(item).find('.mode-select').attr('name', `courses[${index}].mode`);
        $(item).find('.faculty-select').attr('name', `courses[${index}].facultyId`);
        $(item).find('.faculty-select-disabled').attr('name', `courses[${index}].facultyId`);
        $(item).find('.week-select').attr('name', `courses[${index}].week`);
        $(item).find('.department-select').attr('name', `courses[${index}].departmentId`);
        $(item).find('.department-select-disabled').attr('name', `courses[${index}].departmentId`);
        $(item).find('.start-week').attr('name', `courses[${index}].startWeek`);
        $(item).find('.specialty-select').attr('name', `courses[${index}].specialtyId`);
        $(item).find('.specialty-select-disabled').attr('name', `courses[${index}].specialtyId`);
        $(item).find('.end-week').attr('name', `courses[${index}].endWeek`);
        $(item).find('.year-select').attr('name', `courses[${index}].year`);
        $(item).find('.hours-per-week').attr('name', `courses[${index}].hoursPerWeek`);
        $(item).find('.room-select').attr('name', `courses[${index}].roomId`);
        $(item).find('.meetings-per-week').attr('name', `courses[${index}].meetingsPerWeek`);
        $(item).find('.teacher-select').attr('name', `courses[${index}].teacherId`);

        $(item).find('.group').each((i, g) => $(g).attr('name', `courses[${index}].groups[${i}]`));

        $(item).find('.course-time').each((i, ct) => {
            $(ct).find('.course-time-day').attr('name', `courses[${index}].courseTimes[${i}].day`);
            $(ct).find('.course-start-time').attr('name', `courses[${index}].courseTimes[${i}].startTime`);
            $(ct).find('.course-end-time').attr('name', `courses[${index}].courseTimes[${i}].endTime`);
        });
    }
});