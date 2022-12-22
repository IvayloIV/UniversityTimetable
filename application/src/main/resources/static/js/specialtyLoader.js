$(window).on('load', () => {
    var facultySelect = $('.faculty-select');
    var departmentSelect = $('.department-select');
    var specialtySelect = $('.specialty-select');

    if (facultySelect.children().length > 0) {
        loadDepartments(facultySelect, departmentSelect, specialtySelect);
    }

    facultySelect.on('change', () => loadDepartments(facultySelect, departmentSelect, specialtySelect));
    departmentSelect.on('change', () => loadSpecialties(departmentSelect, specialtySelect));
});

function loadDepartments(facultySelect, departmentSelect, specialtySelect) {
    var facultyId = facultySelect.find(":selected").val();
    if (facultyId > -1) {
        $.get(`/department/faculty/${facultyId}`)
            .then((res) => {
                var departmentIdInput = $('#department-id');
                if (departmentSelect.children().first().val() == -1) {
                    $(departmentSelect.children().splice(1)).each((i, e) => e.remove());
                } else {
                    departmentSelect.empty();
                }
                res.forEach(d => departmentSelect.append(
                    $('<option>').val(d.id).text(d.nameBg)
                        .attr("selected", departmentIdInput.val() == d.id)
                ));
                departmentIdInput.val('');
                loadSpecialties(departmentSelect, specialtySelect);
            });
    } else {
        $(departmentSelect.children().splice(1)).each((i, e) => e.remove());
        $(specialtySelect.children().splice(1)).each((i, e) => e.remove());
    }
}

function loadSpecialties(departmentSelect, specialtySelect) {
    var departmentId = departmentSelect.find(":selected").val();
    if (departmentId > -1) {
        $.get(`/specialty/department/${departmentId}`)
            .then((res) => {
                var specialtyIdInput = $('#specialty-id');
                if (specialtySelect.children().first().val() == -1) {
                    $(specialtySelect.children().splice(1)).each((i, e) => e.remove());
                } else {
                    specialtySelect.empty();
                }
                res.forEach(s => specialtySelect.append(
                    $('<option>').val(s.id).text(s.specialtyName)
                        .attr("selected", specialtyIdInput.val() == s.id)
                ));
                specialtyIdInput.val('');
            });
    } else {
        $(specialtySelect.children().splice(1)).each((i, e) => e.remove());
    }
}