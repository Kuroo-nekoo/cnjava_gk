$(document).ready(function () {
    const btnFilter = $(".btn-filter");

    $("#minPrice").on('change', function (e) {
        const minPrice = $(this).val();
        const maxPrice = $("#maxPrice").val();
        if (minPrice !== '' && maxPrice !== '') {
            console.log("minPrice: " + minPrice);
            console.log("maxPrice: " + maxPrice);
            if (parseInt(minPrice) < parseInt(maxPrice)) {
                $("#minPriceVal").text(minPrice);
            }
        }
    })

    $("#maxPrice").on('change', function (e) {
        const minPrice = $("#minPrice").val();
        const maxPrice = $(this).val();
        if (minPrice !== '' && maxPrice !== '') {
            console.log("minPrice: " + minPrice);
            console.log("maxPrice: " + maxPrice);
            if (parseInt(minPrice) < parseInt(maxPrice)) {
                $("#maxPriceVal").text(maxPrice);
            }
        }
    })

    btnFilter.click(function (e) {
        e.preventDefault();

        const colors = $("input[name='color']:checked").map(function () {
            return this.value;
        });

        const brands = $("input[name='brand']:checked").map(function () {
            return this.value;
        });

        console.log(colors.get());
        console.log(brands.get());

        const minPrice = $("#minPrice").val();
        const maxPrice = $("#maxPrice").val();
        let url = '/?filter=';
        if (colors.get().length !== 0) {
            url += `colors:${colors.get()}`;
        }
        if (brands.get().length !== 0) {
            if (colors.get().length !== 0) {
                url += `;brands:${brands.get()}`;
            } else {
                url += `brands:${brands.get()}`;
            }
        }
        if (minPrice !== '' && minPrice !== '0') {
            if (url.endsWith('filter=')) {
                url += `price:${minPrice}`;
            } else if (url.endsWith(';')) {
                url += `price:${minPrice}`;
            } else {
                url += `;price:${minPrice}`;
            }
        }

        if (maxPrice !== '' && maxPrice !== '0') {
            if (url.endsWith('filter=')) {
                url += `price:-${maxPrice}`;
            } else if (
                url.endsWith(';') ||
                url.endsWith(`price:${minPrice}`)
            ) {
                url += `-${maxPrice}`;
            }
        }

        window.location.href = url;
    })
})