package com.ecommerce.controller;

import com.ecommerce.event.ResourceCreatedEvent;
import com.ecommerce.model.Product;
import com.ecommerce.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@Validated
@RequestMapping(path = "/products")
@Api(value = "Administração de Produtos")
public class ProductController {
    private final ProductService productService;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public ProductController(ProductService productService, ApplicationEventPublisher publisher) {
        this.productService = productService;
        this.publisher = publisher;
    }

    @ApiOperation(value = "Retornar todos os produtos", response = ProductService.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Produtos retornados"),
            @ApiResponse(code = 400, message = "Nenhum produto existente")

    })
    @GetMapping
    public List<Product> findAll() {
        return this.productService.findAll();
    }

    @ApiOperation(value = "Retornar produtos", response = ProductService.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Produto retornado"),
            @ApiResponse(code = 400, message = "Produto inexistente")

    })
    @GetMapping(path = "/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Product product = this.productService.findById(id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Cadastrar produtos", response = ProductService.class)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Produto Cadastrado"),
            @ApiResponse(code = 400, message = "Algum campo está incorreto")

    })

    @PostMapping
    public ResponseEntity<Product> save(@Valid @RequestBody Product product, HttpServletResponse response) {

        Product productSave = this.productService.save(product);
        publisher.publishEvent(new ResourceCreatedEvent(this, response, productSave.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(productSave);
    }

    @ApiOperation(value = "Atualizar dados do produto", response = ProductService.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Dados do produto atualizado"),
            @ApiResponse(code = 400, message = "Algum campo está incorreto")

    })
    @PutMapping(path = "/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody @Valid Product product) {
        return ResponseEntity.ok().body(this.productService.update(product, id));
    }

    @ApiOperation(value = "Deletar produto", response = ProductService.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Produto Deletado"),
            @ApiResponse(code = 400, message = "Algum campo está incorreto")

    })

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Product> delete(@PathVariable Long id) {
        Product product = this.productService.delete(id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }
}
