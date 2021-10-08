$(function () {
    loadDataFromServlet();
});

function loadDataFromServlet() {
    $.ajax({
        type: 'GET',
        url: '/todo/index.do',
        data: {
            completed: $("#completed")[0].checked
        },
        dataType: 'json'
    }).done(function (data) {
        loadTasks(data[0]);
        loadCategories(data[1]);
    }).fail(function (err) {
        console.log(err);
    });
}

function loadCategories(categories) {
    $("#category").empty();
    for (let category of categories) {
        let option = document.createElement('option');
        option.id = category.id;
        option.innerText = category.name;
        $("#category").append(option);
    }
}

function loadTasks(tasks) {
    $("#table_body").empty();
    for (let task of tasks) {
        const options = {year: 'numeric', month: 'long', day: 'numeric', hour: 'numeric', minute: "numeric"};
        let created = new Date(task.created).toLocaleDateString("ru", options);
        let checked = task.done ? "checked" : "";
        let user = task.user.name;
        let category = '<ul>';
        for (let cat of task.categories) {
            category += '<li>' + cat.name + '</li>';
        }
        category += '</ul>';
        let row = `
                   <tr>
                      <td>${task.describe}</td>
                      <td>${created}</td>
                      <td>
                         <div class="change_state_div" id="change_state_${task.id}">
                            <input hidden name="id" value="${task.id}">
                            <input type="checkbox" name="state" ${checked}>
                        </div>
                      </td>
                      <td>${user}</td>
                      <td>${category}</td>
                    </tr>`;
        $("#table_body").append(row);
    }
}

function validateDescTask() {
    return $('#newDesc').val() !== '';
}

function validateCategories() {
    let cIdc = [];
    for (let children of $("#category").children()) {
        if (children.selected === true) {
            cIdc.push(children.id);
        }
    }
    if (cIdc.length > 0) {
        return true;
    } else if (cIdc.length === 0) {
        return false
    }
}

function addNewTask() {
    if (validateDescTask() && validateCategories()) {
        let cIdc = [];
        for (let children of $("#category").children()) {
            if (children.selected === true) {
                cIdc.push(children.id);
            }
        }
        $.ajax({
            type: 'POST',
            url: '/todo/index.do',
            data: {
                describe: $('#newDesc').val(),
                cIdc: JSON.stringify(cIdc)
            },
            dataType: 'json'
        }).done(function () {
            $("#newDesc").val("");
            loadDataFromServlet();
        }).fail(function (err) {
            console.log(err);
        });
    }
}

$(document).on("change", "#completed", function () {
        loadDataFromServlet();
    }
);

$(document).on("change", ".change_state_div", function () {
        let id = $($(this).children()[0]).attr("value");
        let done = $($(this).children()[1])[0].checked;
        $.ajax({
            type: 'POST',
            url: '/todo/tasked.do',
            data: {
                id: id,
                done: done
            }
        }).done(function () {
            loadDataFromServlet();
        }).fail(function (err) {
            console.log(err)
        })
    }
);