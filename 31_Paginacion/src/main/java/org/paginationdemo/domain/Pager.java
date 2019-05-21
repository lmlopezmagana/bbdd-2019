package org.paginationdemo.domain;

/**
 * Utilizada para calcular los enlaces a las diferentes páginas que serán mostrados en la paǵina
 *
 * @author Branislav Lazic
 * @author Bruno Raljic
 */
public class Pager {

	// Número inicial de botones a mostrar 
    private int buttonsToShow = 5;

    // Página inicial
    private int startPage;

    // Página final
    private int endPage;

    /**
     * 
     * Constructor del objeto. 
     * 
     * @param totalPages Número total de páginas
     * @param currentPage Página actual
     * @param buttonsToShow Número de botones a mostrar
     */
    public Pager(int totalPages, int currentPage, int buttonsToShow) {

    	// Establecemos el número de botones a mostrar
        setButtonsToShow(buttonsToShow);

        // Calculamos la mitad de las páginas a mostrar
        int halfPagesToShow = getButtonsToShow() / 2;

        // Si el número total de páginas es menor o igual que el número de botones a mostrar
        if (totalPages <= getButtonsToShow()) {
        	// Establecemos la página inicial a 1
            setStartPage(1);
            // Establecemos la página final al número total.
            setEndPage(totalPages);
        // Si la página actual está entre 1 y la mitad
        } else if (currentPage - halfPagesToShow <= 0) {
        	// Establecemos la página inicial a 1
            setStartPage(1);
            // Establecemos la página final al número total de botones a mostrar
            setEndPage(getButtonsToShow());
        // Si la página actual está en la mitad del total de páginas    
        } else if (currentPage + halfPagesToShow == totalPages) {
        	// Establecemos la página inicial de forma que la página actual
        	// queda en el intervalo [currentPage - halfPagesToShow --> currentPage <-- totalPages]
            setStartPage(currentPage - halfPagesToShow);
            // Establecemos la página final
            setEndPage(totalPages);
        // Si la página actual está en el intervalo [halfPagesToShow, totalPages]
        } else if (currentPage + halfPagesToShow > totalPages) {
        	// Establecemos la página inicial en un valor más que el número de botones
        	// a mostrar menos el total de páginas
            setStartPage(totalPages - getButtonsToShow() + 1);
            // Establecemos la página final
            setEndPage(totalPages);
        // En otro caso
        } else {
        	// Establecemos la página inicial y final de forma que la página actual 
        	// queda en el intervalo [currentPage - halfPagesToShow --> currentPage <-- currentPage + halfPagesToShow]
            setStartPage(currentPage - halfPagesToShow);
            setEndPage(currentPage + halfPagesToShow);
        }

    }

    /**
     * Devuelve el número de botones a mostrar
     * 
     * @return int Número de botones
     */
    public int getButtonsToShow() {
        return buttonsToShow;
    }

    /**
     * Método que establece el número de botones a mostrar. Como restricción,
     * se impone que este número tenga que ser par.
     * 
     * @param buttonsToShow Número de botones
     */
    public void setButtonsToShow(int buttonsToShow) {
        if (buttonsToShow % 2 != 0) {
            this.buttonsToShow = buttonsToShow;
        } else {
            throw new IllegalArgumentException("Must be an odd value!");
        }
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    @Override
    public String toString() {
        return "Pager [startPage=" + startPage + ", endPage=" + endPage + "]";
    }

}
