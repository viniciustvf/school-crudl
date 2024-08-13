export class DateUtil {
    static stringToDate(dateStr: string, format: string): Date | null {
      try {
        const parts = dateStr.split('/');
        let day: number, month: number, year: number;
  
        if (format === 'dd/MM/yyyy') {
          [day, month, year] = parts.map(part => parseInt(part, 10));
        } else if (format === 'MM/dd/yyyy') {
          [month, day, year] = parts.map(part => parseInt(part, 10));
        } else if (format === 'yyyy/MM/dd') {
          [year, month, day] = parts.map(part => parseInt(part, 10));
        }else {
          console.error('Unsupported date format:', format);
          return null;
        }
  
        return new Date(year, month - 1, day);
      } catch (error) {
        console.error('Error parsing date:', dateStr, 'with format:', format, error);
        return null;
      }
    }
  }
  