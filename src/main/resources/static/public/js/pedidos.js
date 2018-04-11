//function recuperarClientes(){
//	$.ajax({
//		type: "GET",
//		url: "/pedidos/pclientes",
//		data: "$format=json",
//		dataType: "json",
//		success: function(data){
//			$.each(data,function(d,cliente){
//				console.log(cliente.nome);
//			}) 
//		}
//	});
//}

function marcarCliente(cliente){	
	$.ajax({
		type: "GET",
		url: "/pedidos/checkCliente/"+cliente,
		data: "$format=json",
		dataType: "json",
		success: function(cliente){
			console.log(cliente.nome);			
			document.getElementById('clienteMarcado').innerHTML  = '';
			document.getElementById('clienteMarcado').innerHTML += "Cliente selecionado: "+cliente.nome;
		}
	});
}

function marcarItem(item){	
	$.ajax({
		type: "GET",
		url: "/pedidos/checkItem/"+item,
		data: "$format=json",
		dataType: "json",
		success: function(data){
			console.log(data);					
		}
	});
}	