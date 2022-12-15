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
    $.get(`/department/faculty/${facultyId}`)
        .then((res) => {
            departmentSelect.empty();
            res.forEach(d => departmentSelect.append(
                $('<option>').val(d.id).text(d.nameBg)
            ));
            loadSpecialties(departmentSelect, specialtySelect);
        });
}

function loadSpecialties(departmentSelect, specialtySelect) {
    var departmentId = departmentSelect.find(":selected").val();
    $.get(`/specialty/department/${departmentId}`)
        .then((res) => {
            specialtySelect.empty();
            res.forEach(s => specialtySelect.append(
                $('<option>').val(s.id).text(s.specialtyName)
            ));
        });
}