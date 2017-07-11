var adminFunc = function () {

    var navBtnKomponente =  $("#navigation-komponente");
    var navBtnKategorije = $("#navigation-kategorije");
    var navBtnUredjaji = $("#navigation-uredjaji");
    var navBtnKonfigurator = $("#navigation-konfigurator");

    var windowKomponente = $("#main-content-komponente");
    var windowKategorije = $("#main-content-kategorije");
    var windowUredjaji = $("#main-content-uredjaji");
    var windowKonfigurator = $("#main-content-konfigurator");

    
    var filterKomponente = $("#filter-panel-komponente");
    var addEditKomponente = $("#add-new-komponente-window");
    
    var addEditKategorije = $("#add-new-kategorije-window");
    
    var filterUredjaji = $("#filter-panel-uredjaji");
    var editUredjaji = $("#add-new-uredjaji-window");

    // key listeners

    $(document).keyup(function(e) {
     if (e.keyCode == 27) { // escape key maps to keycode `27`
         addEditKomponente.css("display", "none");
         addEditKomponente.find("form").trigger("reset");
         
         filterKomponente.css("display", "none");
         filterKomponente.find("form").trigger("reset");
         
         addEditKategorije.css("display", "none");
         addEditKategorije.find("form").trigger("reset");
         
         editUredjaji.css("display", "none");
         editUredjaji.find("form").trigger("reset");
         
         filterUredjaji.css("display", "none");
         filterUredjaji.find("form").trigger("reset");
        }
    });

    // show/hide functions

    function showKomponenteWindow(){
        loadAllComponents();
        windowKomponente.css("display","block");
        windowKategorije.css("display","none");
        windowUredjaji.css("display","none");
        windowKonfigurator.css("display","none");
    }

    function showKategorijeWindow(){
        loadAllCategories();
        windowKategorije.css("display","block");
        windowKomponente.css("display","none");
        windowUredjaji.css("display","none");
        windowKonfigurator.css("display","none");
    }

    function showUredjajiWindow(){
        loadAllMachines();
        windowUredjaji.css("display","block");
        windowKategorije.css("display","none");
        windowKomponente.css("display","none");
        windowKonfigurator.css("display","none");
    }

    function showKonfiguratorWindow(){
        windowKonfigurator.css("display","block");
        windowKomponente.css("display","none");
        windowKategorije.css("display","none");
        windowUredjaji.css("display","none");
    }

    // LEFT-SIDE NAVIGATION

    navBtnKomponente.click(function(){
        showKomponenteWindow();
    });

    navBtnKategorije.click(function(){
        showKategorijeWindow();
    });

    navBtnUredjaji.click(function(){
        showUredjajiWindow();
    });

    navBtnKonfigurator.click(function(){
        showKonfiguratorWindow();
    });


    //  KOMPONENTE MANIPULACIJA
    // ---------------------------------------------------------

    // Add new component +++++++++++++++++++++++++++++++++++++++

    $("#add-new-komponente-button").click(function() {
        addEditKomponente.css("display", "block");
    });
    
    $("#filter-komponente-button").click(function() {
        filterKomponente.css("display", "block");
    });

    $("#add-new-komponente-window-close").click(function() {
        addEditKomponente.css("display", "none");
        addEditKomponente.find("form").trigger("reset");
    });

    // Edit component (live method - dinamic elements) ++++++++++

     $(document).on('click', '.editComponent', function(){
        var editId = $(this).attr('id');
        var pureId = splitMyId(editId);
        var desiredComponent = loadComponentById(pureId);
        addEditKomponente.css("display", "block");

        fillFormKomponente(desiredComponent);
     });


    function fillFormKomponente(component){
        addEditKomponente.find("#compImg").val(component.image);
        addEditKomponente.find("#compName").val(component.name);
        addEditKomponente.find("#compPrice").val(component.price.toString());
        addEditKomponente.find("#compQuantity").val(component.availableQuantity.toString());
        addEditKomponente.find("#compDescr").val(component.description);
        addEditKomponente.find("#select-kategorijeZaKomponente").val(component.category.code.toString());
        addEditKomponente.find("#compManLink").val(component.linkManifacturer);
        addEditKomponente.find("#compCode").val(component.code);
        addEditKomponente.find("#compPurpose").val("update");
    }


    // Delete component +++++++++++++++++++++++++++++++++++++++++++

    $(document).on('click', '.deleteComponent', function(){
        var deleteId = $(this).attr('id');
        var pureId = splitMyId(deleteId);
        $.ajax({
            url: 'ComponentController',
            method: "POST",
            data:{purpose:"delete",
                 compId:pureId},
            success: function (response){
                window.location = "/WebShopEcliptica/admin-panel.html";
            }
        });
    });





    //  KATEGORIJE MANIPULACIJA
    // ---------------------------------------------------------



    $("#add-new-kategorije-button").click(function() {
        addEditKategorije.css("display", "block");
        addEditKategorije.find("#catPurpose").val("add");
    });

    $("#add-new-kategorije-window-close").click(function() {
        addEditKategorije.css("display", "none");
        addEditKategorije.find("form").trigger("reset");
    });


    // Add new category +++++++++++++++++++++++++++

    $('#add-new-kategorije-form').submit(function(event){
        var category = JSON.stringify($(this).serializeObject());
        var formPurpose = $(this).find("#catPurpose").val();
        $.ajax({
            method: "POST",
            url: "CategoryController",
            data: {purpose: formPurpose,
                   categoryJson: category},
            success: function(){
                loadAllCategories();
                loadCategoriesForSelect();
                addEditKategorije.css("display", "none");
                addEditKategorije.find("form").trigger("reset");
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
	            alert(XMLHttpRequest.status);
	            alert(errorThrown);
	        }
        });
        event.preventDefault();
    });




    // Edit existing category +++++++++++++++++++++++++++

    $(document).on('click', '.editCategory', function(){
        var editId = $(this).attr('id');
        var pureId = splitMyId(editId);
        var desiredCategory = loadCategoryById(pureId);
        addEditKategorije.css("display", "block");
        fillFormKategorije(desiredCategory);
     });


    function fillFormKategorije(category){
        addEditKategorije.find("#catName").val(category.name);
        addEditKategorije.find("#catDescr").val(category.description);
        addEditKategorije.find("#select-podkategorijeZaKategorije").val(category.subcategory.code.toString());
        addEditKategorije.find("#catCode").val(category.code);
        addEditKategorije.find("#catPurpose").val("update");
    }


    // Delete category +++++++++++++++++++++++++++


    $(document).on('click', '.deleteCategory', function(){
        var deleteId = $(this).attr('id');
        var pureId = splitMyId(deleteId);
        $.ajax({
            url: 'CategoryController',
            method: "POST",
            data:{purpose:"delete",
                 catId:pureId},
            success: function (response){
                loadAllCategories();
                loadCategoriesForSelect();
            }
        });
    });







    //  UREDJAJI MANIPULACIJA
    // ---------------------------------------------------------

    $("#add-new-uredjaji-button").click(function() {
        $("#add-new-uredjaji-window").css("display", "block");
    });

    $("#add-new-uredjaji-window-close").click(function() {
        $("#add-new-uredjaji-window").css("display", "none");
        $("#add-new-uredjaji-window").find("form").trigger("reset");
    });


    // Add new machine ++++++++++++++++++++++++++++++++++++++++++++

   
    // create new one from configurator
     $('#configuration-machine').submit(function(event){
        var machine = JSON.stringify($(this).serializeObject());
        $.ajax({
            method: "POST",
            url: "CustomMachineController",
            data: {purpose: "add",
                   customMachineJson: machine},
            success: function(){
                alert("Kreirali ste uredjaj!");
                $('#conf-device-name').val('');
                $('#conf-device-descr').val('');
                loadAllMachines();
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
	            alert(XMLHttpRequest.status);
	            alert(errorThrown);
	        }
        });
        event.preventDefault();
    });
    
    
    
     // edit existing one
    $('#edit-existing-uredjaji-form').submit(function(event){
        var machine = JSON.stringify($(this).serializeObject());
        $.ajax({
            method: "POST",
            url: "CustomMachineController",
            data: {purpose: "update",
                   customMachineJson: machine},
            success: function(){
                loadAllMachines();
                editUredjaji.css("display", "none");
                editUredjaji.find("form").trigger("reset");
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
	            alert(XMLHttpRequest.status);
	            alert(errorThrown);
	        }
        });
        event.preventDefault();
    });
    
    
    $("#filter-uredjaji-button").click(function() {
        filterUredjaji.css("display", "block");
    });


    // Edit machine +++++++++++++++++++++++++++++++++++++++
    

      $(document).on('click', '.editMachine', function(){
        var editId = $(this).attr('id');
        var pureId = splitMyId(editId);
        var desiredMachine = loadMachineById(pureId);
        editUredjaji.css("display", "block");
        
        fillFormUredjaji(desiredMachine); 
     });


    
    function fillFormUredjaji(machine){
        editUredjaji.find("#machName").val(machine.name);
        editUredjaji.find("#machDescr").val(machine.description);
        
        var resultedComp = $.grep(machine.components, function(e){ return e.category.name == "CPU"; });
        editUredjaji.find("#select-devEditCPU").val(resultedComp[0].code);
        var resultedComp = $.grep(machine.components, function(e){ return e.category.name == "Motherboard"; });
        editUredjaji.find("#select-devEditMB").val(resultedComp[0].code);
        var resultedComp = $.grep(machine.components, function(e){ return e.category.name == "HDD"; });
        editUredjaji.find("#select-devEditHDD").val(resultedComp[0].code);
        var resultedComp = $.grep(machine.components, function(e){ return e.category.name == "RAM"; });
        editUredjaji.find("#select-devEditRAM").val(resultedComp[0].code);
        var resultedComp = $.grep(machine.components, function(e){ return e.category.name == "Graphic card"; });
        editUredjaji.find("#select-devEditVGA").val(resultedComp[0].code);
        var resultedComp = $.grep(machine.components, function(e){ return e.category.name == "Casing"; });
        editUredjaji.find("#select-devEditCasing").val(resultedComp[0].code);
        editUredjaji.find("#machCode").val(machine.code);
    }

    // Delete machine +++++++++++++++++++++++++++++++++++++

    
    $(document).on('click', '.deleteMachine', function(){
        var deleteId = $(this).attr('id');
        var pureId = splitMyId(deleteId);
        $.ajax({
            url: 'CustomMachineController',
            method: "POST",
            data:{purpose: "delete",
                  machineId: pureId},
            success: function (response){
                loadAllMachines();
            }
        });
    });


    


    // FILTERS ===================================
    //============================================
    
    
    // filtering components
    $("#filter-triggerKomponente").submit(function(){
        var filterBundle = JSON.stringify($(this).serializeObject());
        $.ajax({
            method: "GET",
            url: "ComponentController",
            dataType: "json",
            data: {purpose: "filterByAll",
                   filterBundleJson: filterBundle},
            success: function(response){
                var components = response;
                if (!components[0]){
                    alert("Rezultati pretrage prazni!");
                }else{
                    $('#tabela-rezultati-komponente > tbody').empty();
                    var tr;
                    for (var i = 0; i < components.length; i++) {
                        var editID = "edit-" + components[i].code.toString();
                        var deleteID = "delete-" + components[i].code.toString();
                        var tabAction = $('<div style="margin-right:5px; margin-top:8px;"><a href="#" class="fa fa-pencil fa-2x editComponent" id='+editID+' aria-hidden="true"></a><span style="font-size:2.2em;"> |</span><a href="#" class="fa fa-times fa-2x deleteComponent" id='+deleteID+' aria-hidden="true"/></div>');
                        tr = $('<tr/>');
                        tr.append("<td>" + components[i].code + "</td>");
                        tr.append("<td>" + "<img src=" + components[i].image + " class='tabela-rezultati-slika'>" + "</td>");
                        tr.append("<td>" + components[i].name + "</td>");
                        tr.append("<td>" + components[i].price + "</td>");
                        tr.append("<td>" + components[i].availableQuantity + "</td>");
                        tr.append("<td style='word-break:break-all;'>" + components[i].description + "</td>");
                        if(!components[i].category){
                            tr.append("<td>No category</td>");
                        }else{
                            tr.append("<td>" + components[i].category.name + "</td>");
                        }
                        tr.append("<td>" + "<a href=" + components[i].linkManifacturer + ">" + components[i].linkManifacturer + "</a>" + "</td>");
                        tr.append(tabAction);
                        $('#tabela-rezultati-komponente').find('tbody').append(tr);
                        }
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
	            alert(XMLHttpRequest.status);
	            alert(errorThrown);
	        }
        });
        event.preventDefault();
    });
    
    
    
    //filtering machines

    $("#filter-triggerUredjaji").submit(function(event){
        var filterBundle = JSON.stringify($(this).serializeObject());
        $.ajax({
            method: "GET",
            url: "CustomMachineController",
            dataType: "json",
            data: {purpose: "filterByAll",
                   filterBundleJson: filterBundle},
            success: function(response){
                var machines = response;
                if (!machines[0]){
                    alert("Rezultati pretrage prazni!");
                }else{
                    $('#tabela-rezultati-uredjaji > tbody').empty();
                    var tr;
                    for (var i = 0; i < machines.length; i++) {
                        var editID = "edit-" + machines[i].code.toString();
                        var deleteID = "delete-" + machines[i].code.toString();
                        var tabAction = $('<div style="margin-right:5px; margin-top:8px;"><a href="#" class="fa fa-pencil fa-2x editMachine" id='+editID+' aria-hidden="true"></a><span style="font-size:2.2em;"> |</span><a href="#" class="fa fa-times fa-2x deleteMachine" id='+deleteID+' aria-hidden="true"/></div>');

                        tr = $('<tr/>');
                        tr.append("<td>" + machines[i].code + "</td>");
                        tr.append("<td>" + machines[i].name + "</td>");
                        tr.append("<td>" + machines[i].description + "</td>");
                        //for (var comp in machines[i].components) {

                        var resultedComp = $.grep(machines[i].components, function(e){ if(e){return e.category.name == "CPU"; }});
                        if(resultedComp[0]){
                            tr.append("<td>" + resultedComp[0].name + "</td>");
                        }else{tr.append("<td>No component</td>");}
                        var resultedComp = $.grep(machines[i].components, function(e){ if(e){return e.category.name == "Motherboard"; }});
                        if(resultedComp[0]){
                            tr.append("<td>" + resultedComp[0].name + "</td>");
                        }else{tr.append("<td>No component</td>");}
                        var resultedComp = $.grep(machines[i].components, function(e){ if(e){return e.category.name == "HDD"; }});
                        if(resultedComp[0]){
                            tr.append("<td>" + resultedComp[0].name + "</td>");
                        }else{tr.append("<td>No component</td>");}
                        var resultedComp = $.grep(machines[i].components, function(e){ if(e){return e.category.name == "RAM"; }});
                        if(resultedComp[0]){
                            tr.append("<td>" + resultedComp[0].name + "</td>");
                        }else{tr.append("<td>No component</td>");}
                        var resultedComp = $.grep(machines[i].components, function(e){ if(e){return e.category.name == "Graphic card"; }});
                        if(resultedComp[0]){
                            tr.append("<td>" + resultedComp[0].name + "</td>");
                        }else{tr.append("<td>No component</td>");}
                        var resultedComp = $.grep(machines[i].components, function(e){ if(e){return e.category.name == "Casing"; }});
                        if(resultedComp[0]){
                            tr.append("<td>" + resultedComp[0].name + "</td>");
                        }else{tr.append("<td>No component</td>");}
                        //} 
                        tr.append(tabAction);
                        $('#tabela-rezultati-uredjaji').find('tbody').append(tr);
                    }
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
	            alert(XMLHttpRequest.status);
	            alert(errorThrown);
	        }
        });
        event.preventDefault();
    });


    // SEARCH FILTERS===================================
    //==================================================
    
    
    $("#searchFormKomponente").submit(function(event){
        var searchText = $("#searchTextKomponente").val();
        $.ajax({
            method: "GET",
            url: "ComponentController",
            dataType: "json",
            data: {purpose: "getByName",
                   searchInput: searchText},
            success: function(response){
                $('#tabela-rezultati-komponente > tbody').empty();
                var components = response;
                 if (!components){
                        alert("Rezultati pretrage prazni!");
                    }else{
                        var tr;
                        for (var i = 0; i < components.length; i++) {
                            var editID = "edit-" + components[i].code.toString();
                            var deleteID = "delete-" + components[i].code.toString();
                            var tabAction = $('<div style="margin-right:5px; margin-top:8px;"><a href="#" class="fa fa-pencil fa-2x editComponent" id='+editID+' aria-hidden="true"></a><span style="font-size:2.2em;"> |</span><a href="#" class="fa fa-times fa-2x deleteComponent" id='+deleteID+' aria-hidden="true"/></div>');
                            tr = $('<tr/>');
                            tr.append("<td>" + components[i].code + "</td>");
                            tr.append("<td>" + "<img src=" + components[i].image + " class='tabela-rezultati-slika'>" + "</td>");
                            tr.append("<td>" + components[i].name + "</td>");
                            tr.append("<td>" + components[i].price + "</td>");
                            tr.append("<td>" + components[i].availableQuantity + "</td>");
                            tr.append("<td style='word-break:break-all;'>" + components[i].description + "</td>");
                            if(!components[i].category){
                                tr.append("<td>No category</td>");
                            }else{
                                tr.append("<td>" + components[i].category.name + "</td>");
                            }
                            tr.append("<td>" + "<a href=" + components[i].linkManifacturer + ">" + components[i].linkManifacturer + "</a>" + "</td>");
                            tr.append(tabAction);
                            $('#tabela-rezultati-komponente').find('tbody').append(tr);
                        }
                    }   
                
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
	            alert(XMLHttpRequest.status);
	            alert(errorThrown);
	        }
        });
        event.preventDefault();
    });
    
    
    $("#searchFormUredjaji").submit(function(event){
        var searchText = $("#searchTextUredjaji").val();
        
        $.ajax({
            method: "GET",
            url: "CustomMachineController",
            dataType: "json",
            data: {purpose: "getByName",
                   searchInput: searchText},
            success: function(response){
                $('#tabela-rezultati-uredjaji > tbody').empty();
                var machines = response;
                alert(JSON.stringify(machines));
                if (!machines){
                    alert("Rezultati pretrage prazni!");
                }else{
                    var tr;
                    for (var i = 0; i < machines.length; i++) {
                        var editID = "edit-" + machines[i].code.toString();
                        var deleteID = "delete-" + machines[i].code.toString();
                        var tabAction = $('<div style="margin-right:5px; margin-top:8px;"><a href="#" class="fa fa-pencil fa-2x editMachine" id='+editID+' aria-hidden="true"></a><span style="font-size:2.2em;"> |</span><a href="#" class="fa fa-times fa-2x deleteMachine" id='+deleteID+' aria-hidden="true"/></div>');

                        tr = $('<tr/>');
                        tr.append("<td>" + machines[i].code + "</td>");
                        tr.append("<td>" + machines[i].name + "</td>");
                        tr.append("<td>" + machines[i].description + "</td>");
                        //for (var comp in machines[i].components) {

                        var resultedComp = $.grep(machines[i].components, function(e){ if(e){return e.category.name == "CPU"; }});
                        if(resultedComp[0]){
                            tr.append("<td>" + resultedComp[0].name + "</td>");
                        }else{tr.append("<td>No component</td>");}
                        var resultedComp = $.grep(machines[i].components, function(e){ if(e){return e.category.name == "Motherboard"; }});
                        if(resultedComp[0]){
                            tr.append("<td>" + resultedComp[0].name + "</td>");
                        }else{tr.append("<td>No component</td>");}
                        var resultedComp = $.grep(machines[i].components, function(e){ if(e){return e.category.name == "HDD"; }});
                        if(resultedComp[0]){
                            tr.append("<td>" + resultedComp[0].name + "</td>");
                        }else{tr.append("<td>No component</td>");}
                        var resultedComp = $.grep(machines[i].components, function(e){ if(e){return e.category.name == "RAM"; }});
                        if(resultedComp[0]){
                            tr.append("<td>" + resultedComp[0].name + "</td>");
                        }else{tr.append("<td>No component</td>");}
                        var resultedComp = $.grep(machines[i].components, function(e){ if(e){return e.category.name == "Graphic card"; }});
                        if(resultedComp[0]){
                            tr.append("<td>" + resultedComp[0].name + "</td>");
                        }else{tr.append("<td>No component</td>");}
                        var resultedComp = $.grep(machines[i].components, function(e){ if(e){return e.category.name == "Casing"; }});
                        if(resultedComp[0]){
                            tr.append("<td>" + resultedComp[0].name + "</td>");
                        }else{tr.append("<td>No component</td>");}
                        //} 
                        tr.append(tabAction);
                        $('#tabela-rezultati-uredjaji').find('tbody').append(tr);
                    }
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
	            alert(XMLHttpRequest.status);
	            alert(errorThrown);
	        }
        });
        event.preventDefault();
    });



}










//  initial AJAX CALL FUNCTIONS

//  komponente window


function loadAllComponents(){
    $('#tabela-rezultati-komponente > tbody').empty();
    $.ajax({
    method: "GET",
    url: "ComponentController",
    dataType: "json",
    success: function (response){
        var components = response;
        var tr;
        for (var i = 0; i < components.length; i++) {
            var editID = "edit-" + components[i].code.toString();
            var deleteID = "delete-" + components[i].code.toString();
            var tabAction = $('<div style="margin-right:5px; margin-top:8px;"><a href="#" class="fa fa-pencil fa-2x editComponent" id='+editID+' aria-hidden="true"></a><span style="font-size:2.2em;"> |</span><a href="#" class="fa fa-times fa-2x deleteComponent" id='+deleteID+' aria-hidden="true"/></div>');
            tr = $('<tr/>');
            tr.append("<td>" + components[i].code + "</td>");
            tr.append("<td>" + "<img src=" + components[i].image + " class='tabela-rezultati-slika'>" + "</td>");
            tr.append("<td>" + components[i].name + "</td>");
            tr.append("<td>" + components[i].price + "</td>");
            tr.append("<td>" + components[i].availableQuantity + "</td>");
            tr.append("<td style='word-break:break-all;'>" + components[i].description + "</td>");
            if(!components[i].category){
                tr.append("<td>No category</td>");
            }else{
                tr.append("<td>" + components[i].category.name + "</td>");
            }
            tr.append("<td>" + "<a href=" + components[i].linkManifacturer + ">" + components[i].linkManifacturer + "</a>" + "</td>");
            tr.append(tabAction);
            $('#tabela-rezultati-komponente').find('tbody').append(tr);
        }
    },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest.status);
            alert(errorThrown);
        }
});
}

function loadComponentById(compId){
    var component = "";
    $.ajax({
        method: "GET",
        url: "ComponentController",
        data: {purpose : "getComponentById",
               componentId : compId},
        dataType: "json",
        async:false,
        success: function(response){
            component = response;
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
        alert(XMLHttpRequest.status);
        alert(errorThrown);
        }
    });
    return component;
}



function loadCategoriesForSelect(){
    var selectOptionsKomponente = $("#select-kategorijeZaKomponente");
    var selectOptionsKategorije = $("#select-podkategorijeZaKategorije");
    
    var selectFilterKomponente = $("#select-filterKomponente");
    selectOptionsKomponente.empty();
    selectOptionsKategorije.empty();
    $.ajax({
        method: "GET",
        url: "CategoryController",
        dataType: "json",
        cache: false,
        success: function(response){
            var categories = response;
            for (var i=0; i<categories.length; i++){
                selectOptionsKomponente.append('<option value=' + categories[i].code.toString() + '>' + categories[i].name + '</option>');
                selectOptionsKategorije.append('<option value=' + categories[i].code.toString() + '>' + categories[i].name + '</option>');
                
                // for filter komponente
                selectFilterKomponente.append('<option value=' + categories[i].code.toString() + '>' + categories[i].name + '</option>');
                
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
	            alert(XMLHttpRequest.status);
	            alert(errorThrown);
	        }
    });
}

//  kategorije window

function loadAllCategories(){
    $('#tabela-rezultati-kategorije > tbody').empty();
    $.ajax({
    method: "GET",
    url: "CategoryController",
    dataType: "json",
    cache: false,
    success: function (response){
        var categories = response;
        var tr;
        for (var i = 0; i < categories.length; i++) {
            var editID = "edit-" + categories[i].code.toString();
            var deleteID = "delete-" + categories[i].code.toString();
            var tabAction = $('<div style="margin-right:5px; margin-top:8px;"><a href="#" class="fa fa-pencil fa-2x editCategory" id='+editID+' aria-hidden="true"></a><span style="font-size:2.2em;"> |</span><a href="#" class="fa fa-times fa-2x deleteCategory" id='+deleteID+' aria-hidden="true"/></div>');
            tr = $('<tr/>');
            tr.append("<td>" + categories[i].code + "</td>");
            tr.append("<td>" + categories[i].name + "</td>");
            tr.append("<td>" + categories[i].description + "</td>");
            tr.append("<td>" + categories[i].subcategory.name + "</td>");
            tr.append(tabAction);
            $('#tabela-rezultati-kategorije').find('tbody').append(tr);
        }
    },
    error: function(XMLHttpRequest, textStatus, errorThrown) {
        alert(XMLHttpRequest.status);
        alert(errorThrown);
    }
});
}


function loadCategoryById(catId){
    var category = "";
    $.ajax({
        method: "GET",
        url: "CategoryController",
        data: {purpose : "getCategoryById",
               categoryId : catId},
        dataType: "json",
        async:false,
        success: function(response){
            category = response;
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
        alert(XMLHttpRequest.status);
        alert(errorThrown);
        }
    });
    return category;
}


//  Custom-built machines window

function loadAllMachines(){
    $('#tabela-rezultati-uredjaji > tbody').empty();
    $.ajax({
        method: "GET",
        url: "CustomMachineController",
        data: {purpose: "getAll"},
        dataType: "json",
        success: function (response){
            var machines = response;
            var tr;
            for (var i = 0; i < machines.length; i++) {
                var editID = "edit-" + machines[i].code.toString();
                var deleteID = "delete-" + machines[i].code.toString();
                var tabAction = $('<div style="margin-right:5px; margin-top:8px;"><a href="#" class="fa fa-pencil fa-2x editMachine" id='+editID+' aria-hidden="true"></a><span style="font-size:2.2em;"> |</span><a href="#" class="fa fa-times fa-2x deleteMachine" id='+deleteID+' aria-hidden="true"/></div>');
                
                tr = $('<tr/>');
                tr.append("<td>" + machines[i].code + "</td>");
                tr.append("<td>" + machines[i].name + "</td>");
                tr.append("<td>" + machines[i].description + "</td>");
                //for (var comp in machines[i].components) {
                
                var resultedComp = $.grep(machines[i].components, function(e){ if(e){return e.category.name == "CPU"; }});
                if(resultedComp[0]){
                    tr.append("<td>" + resultedComp[0].name + "</td>");
                }else{tr.append("<td>No component</td>");}
                var resultedComp = $.grep(machines[i].components, function(e){ if(e){return e.category.name == "Motherboard"; }});
                if(resultedComp[0]){
                    tr.append("<td>" + resultedComp[0].name + "</td>");
                }else{tr.append("<td>No component</td>");}
                var resultedComp = $.grep(machines[i].components, function(e){ if(e){return e.category.name == "HDD"; }});
                if(resultedComp[0]){
                    tr.append("<td>" + resultedComp[0].name + "</td>");
                }else{tr.append("<td>No component</td>");}
                var resultedComp = $.grep(machines[i].components, function(e){ if(e){return e.category.name == "RAM"; }});
                if(resultedComp[0]){
                    tr.append("<td>" + resultedComp[0].name + "</td>");
                }else{tr.append("<td>No component</td>");}
                var resultedComp = $.grep(machines[i].components, function(e){ if(e){return e.category.name == "Graphic card"; }});
                if(resultedComp[0]){
                    tr.append("<td>" + resultedComp[0].name + "</td>");
                }else{tr.append("<td>No component</td>");}
                var resultedComp = $.grep(machines[i].components, function(e){ if(e){return e.category.name == "Casing"; }});
                if(resultedComp[0]){
                    tr.append("<td>" + resultedComp[0].name + "</td>");
                }else{tr.append("<td>No component</td>");}
                //} 
                tr.append(tabAction);
                $('#tabela-rezultati-uredjaji').find('tbody').append(tr);
                
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest.status);
            alert(errorThrown);
        }
    });
}

function loadMachineById(machId){
    var machine = "";
    $.ajax({
        method: "GET",
        url: "CustomMachineController",
        data: {purpose : "getById",
               machineId : machId},
        dataType: "json",
        async:false,
        success: function(response){
            machine = response;
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
        alert(XMLHttpRequest.status);
        alert(errorThrown);
        }
    });
    return machine;
}


// Configurator window

function loadAllComponentsForSelects(){
    var selectOptionsCPU = $("#select-conf-cpu");
    var selectOptionsMB = $("#select-conf-mb");
    var selectOptionsHDD = $("#select-conf-hdd");
    var selectOptionsRAM = $("#select-conf-ram");
    var selectOptionsVGA = $("#select-conf-vga");
    var selectOptionsHouse = $("#select-conf-house");
    var selectOptionsRest = $(".select-conf-rest");
    
    var selectEditCPU = $("#select-devEditCPU");
    var selectEditMB = $("#select-devEditMB");
    var selectEditHDD = $("#select-devEditHDD");
    var selectEditRAM = $("#select-devEditRAM");
    var selectEditVGA = $("#select-devEditVGA");
    var selectEditCasing = $("#select-devEditCasing");
    
    var selectFilterCPU = $("#select-devFilterCPU");
    var selectFilterMB = $("#select-devFilterMB");
    var selectFilterHDD = $("#select-devFilterHDD");
    var selectFilterRAM = $("#select-devFilterRAM");
    var selectFilterVGA = $("#select-devFilterVGA");
    var selectFilterCasing = $("#select-devFilterCasing");
    
    $.ajax({
        method: "GET",
        url: "ComponentController",
        dataType: "json",
        cache: false,
        success: function(response){
            var components = response;
            for (var i=0; i<components.length; i++){
                if (components[i].category){
                   if (components[i].category.name == "CPU"){
                    
                        selectOptionsCPU.append(('<option value=' + components[i].code.toString() + '>' + components[i].name + '</option>'));
                        selectEditCPU.append(('<option value=' + components[i].code.toString() + '>' + components[i].name + '</option>'));
                        selectFilterCPU.append(('<option value=' + components[i].code.toString() + '>' + components[i].name + '</option>'));
                    }else if (components[i].category.name == "Motherboard"){
                        selectOptionsMB.append(('<option value=' + components[i].code.toString() + '>' + components[i].name + '</option>'));
                        selectEditMB.append(('<option value=' + components[i].code.toString() + '>' + components[i].name + '</option>'));
                        selectFilterMB.append(('<option value=' + components[i].code.toString() + '>' + components[i].name + '</option>'));
                    }else if (components[i].category.name == "HDD"){
                        selectOptionsHDD.append(('<option value=' + components[i].code.toString() + '>' + components[i].name + '</option>'));
                        selectEditHDD.append(('<option value=' + components[i].code.toString() + '>' + components[i].name + '</option>'));
                        selectFilterHDD.append(('<option value=' + components[i].code.toString() + '>' + components[i].name + '</option>'));
                    }else if (components[i].category.name == "RAM"){
                        selectOptionsRAM.append(('<option value=' + components[i].code.toString() + '>' + components[i].name + '</option>'));
                        selectEditRAM.append(('<option value=' + components[i].code.toString() + '>' + components[i].name + '</option>'));
                        selectFilterRAM.append(('<option value=' + components[i].code.toString() + '>' + components[i].name + '</option>'));
                    }else if (components[i].category.name == "Graphic card"){
                        selectOptionsVGA.append(('<option value=' + components[i].code.toString() + '>' + components[i].name + '</option>'));
                        selectEditVGA.append(('<option value=' + components[i].code.toString() + '>' + components[i].name + '</option>'));
                        selectFilterVGA.append(('<option value=' + components[i].code.toString() + '>' + components[i].name + '</option>'));
                    }else if (components[i].category.name == "Casing"){
                        selectOptionsHouse.append(('<option value=' + components[i].code.toString() + '>' + components[i].name + '</option>'));
                        selectEditCasing.append(('<option value=' + components[i].code.toString() + '>' + components[i].name + '</option>'));
                        selectFilterCasing.append(('<option value=' + components[i].code.toString() + '>' + components[i].name + '</option>'));
                    }else{
                        selectOptionsRest.append(('<option value=' + components[i].code.toString() + '>' + components[i].name + '</option>'));
                    } 
                }
                
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
	            alert(XMLHttpRequest.status);
	            alert(errorThrown);
	        }
    });
}



// Custom functions

function splitMyId(id){
        return id.split('-')[1];
    }

$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

// -----------------------------------------------------------------------------

  // INITIAL DATA SUCKING

$(document).ready(function() {
    $.ajaxSetup({ cache: false });
    loadAllComponents();
    loadCategoriesForSelect();
    loadAllCategories();
    loadAllComponentsForSelects();
    loadAllMachines();
  });

$(document).ready(adminFunc);
