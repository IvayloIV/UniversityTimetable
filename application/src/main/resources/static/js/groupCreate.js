$(window).on('load', () => {
    $('.create-group').on('click', createGroup);

    function createGroup(e) {
        var addButton = $(e.target);
        if (addButton.hasClass("fa-plus")) {
            addButton = addButton.parent();
        }
        addButton.blur();

        var rows = addButton.closest('.groups-flex');
        var groups = $(rows).find('.group');
        var emptyGroupValidation = $(rows).parent().find('.empty-group-validation');
        var groupExistsValidation = $(rows).parent().find('.group-exists-validation');

        var currentGroup = groups[groups.length - 1];
        var value = $(currentGroup).val();

        var isValid = true;
        if (value.length === 0) {
            emptyGroupValidation.removeClass('d-none');
            isValid = false;
        } else {
            emptyGroupValidation.addClass('d-none');
        }

        var groupsWithTheSameName = groups
            .splice(0, groups.length - 1)
            .filter(g => {
                return $(g).val().toLowerCase() === value.toLowerCase();
            })
        console.log(groupsWithTheSameName);
        console.log(value.toLowerCase());

        if (isValid && groupsWithTheSameName.length !== 0) {
            groupExistsValidation.removeClass('d-none');
            isValid = false;
        }  else {
            groupExistsValidation.addClass('d-none');
        }

        if (!isValid) {
            return;
        }

        var groupsWrapper = $(rows).find('.groups-wrapper');
        $('<span>').addClass('d-flex ms-3')
            .append($('<input>').addClass('form-control group').attr('readonly', 'readonly').attr('style', 'width:50px;').val(value))
            .append($('<button>').attr('type', 'button').addClass('btn bg-danger text-white fw-bold remove-group ms-1').on('click', removeGroup)
                .append($('<i>').addClass('fa-solid fa-trash'))).insertBefore($(currentGroup));

        $(currentGroup).val('');
        updateGroupNames($(rows).find('.group'));
    }

    function removeGroup(e) {
        var addButton = $(e.target);
        if (addButton.hasClass("fa-trash")) {
            addButton = addButton.parent();
        }

        var groupWrapper = addButton.closest('.groups-wrapper');
        addButton.parent().remove();
        updateGroupNames($(groupWrapper).find('.group'));
    }

    function updateGroupNames(groups) {
        var courses = $('.course-wrapper');
        groups.each((i, g) => $(g).attr('name', `courses[${courses.length}].groups[${i}]`));
    }
});