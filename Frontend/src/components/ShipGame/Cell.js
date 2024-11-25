function Cell({ cellValue, onClick }) {
    const isBlocked = cellValue === -1;

    const cellClass = `ship_cell 
        ${cellValue === 1 ? "ship" : ""} 
        ${cellValue === 2 ? "hit" : ""} 
        ${cellValue === -1 ? "blocked" : ""}`;

    return (
        <div
            className={cellClass}
            onClick={isBlocked ? null : onClick}
        />
    );
}

export default Cell;
