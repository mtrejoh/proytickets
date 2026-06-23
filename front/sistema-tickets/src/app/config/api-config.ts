/**
 * Configuración centralizada de la API
 */
export const API_CONFIG = {
  baseUrl: 'http://localhost:8080',
  endpoints: {
    acceso:         '/usuarios/acceso',
    usuarios:       '/usuarios',
    linkDashBoard:  '/tickets/dashboard',
    listar:         '/tickets/listar',
    tickets:        '/tickets/registrar',
    ticketCancel:   '/tickets/cancelar',
    ticketClose:    '/tickets/close',
    ticketsSeg:     '/tickets/seguimiento',
    ticketTomar:    '/tickets/tomarticket',
    userService:    '/usuarios/atender',
    userAtender:    '/usuarios/asignar',
    userCreate:     '/usuarios/crearusr',
    userChangeRol:  '/usuarios/changerol',
    userChgStatus:  '/usuarios/changestatus',
    personalActivo: '/personal/activosys',
    roles:          '/sistema/roles',
    personalistar:  '/personal/listar',
    savePersonal:   '/personal/creapersonal',
    updatePersonal: '/personal/actualizar',
    updateStPersonal: '/personal/actualizar/status',
  },
};

export const getApiUrl = (endpoint: keyof typeof API_CONFIG.endpoints): string => {
  return `${API_CONFIG.baseUrl}${API_CONFIG.endpoints[endpoint]}`;
};
